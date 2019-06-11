package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.nikitamedvedev.application.persistence.AssignmentBindingRepository;
import ru.nikitamedvedev.application.persistence.AssignmentResultRepository;
import ru.nikitamedvedev.application.persistence.StudentUserRepository;
import ru.nikitamedvedev.application.persistence.dto.*;
import ru.nikitamedvedev.application.service.dto.*;
import ru.nikitamedvedev.application.web.dto.AllGradesResponse;
import ru.nikitamedvedev.application.web.dto.FullAssignmentResultResponse;
import ru.nikitamedvedev.application.web.dto.GroupGrades;
import ru.nikitamedvedev.application.web.dto.StudentAssignmentGradeResponse;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static ru.nikitamedvedev.application.hepler.ExceptionUtils.entityNotFound;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {

    private final AssignmentResultRepository assignmentResultRepository;
    private final AssignmentBindingRepository assignmentBindingRepository;
    private final StudentUserRepository studentUserRepository;
    private final SemesterService semesterService;

    private final Converter<AssignmentResultDb, AssignmentResult> assignmentResultConverter;
    private final Converter<AssignmentBindingDb, AssignmentBinding> assignmentBindingConverter;


    public Map<Long, List<AssignmentResult>> findAssignmentResultsForUser(String login) {
        return assignmentResultRepository.findByCreated_Login(login).stream()
                .map(assignmentResultConverter::convert)
                .collect(Collectors.groupingBy(AssignmentResult::getAssignmentBindingId));
    }

    public void addAssignmentResult(Long bindingAssignmentId, String login, String originalFilename, byte[] bytes) {
        val student = studentUserRepository.findById(login).orElseThrow(() -> entityNotFound("student", login));
        val bindingAssignment = assignmentBindingRepository.findById(bindingAssignmentId).orElseThrow(() -> entityNotFound("binding assignment", bindingAssignmentId));

        val assignmentResultDb = new AssignmentResultDb(
                null,
                bindingAssignment,
                student,
                new FileDb(null, originalFilename, bytes),
                "",
                0,
                Status.NEW
        );
        assignmentResultRepository.save(assignmentResultDb);
    }

    public List<StudentGrades> getAssignmentGradesForStudent(String login) {
        Long currentSemester = semesterService.getCurrentSemester().getId();
        StudentUserDb student = studentUserRepository.findById(login).orElseThrow(() -> entityNotFound("student", login));

        return assignmentBindingRepository.findByGroup_IdAndSemester_Id(student.getGroup().getId(), currentSemester)
                .stream()
                .map(assignmentBindingConverter::convert)
                .collect(Collectors.groupingBy(assignmentBinding -> assignmentBinding.getSubject().getName()))
                .entrySet()
                .stream()
                .map(entry -> {
                    String subjectName = entry.getKey();
                    List<AssignmentBinding> bindings = entry.getValue();
                    bindings.sort(Comparator.comparing(AssignmentBinding::getId));
                    Map<String, Grade> assignmentNameToGrade = new LinkedHashMap<>();
                    bindings.forEach(assignmentBinding -> {
                        Optional<AssignmentResultDb> lastResult = getLastResult(login, assignmentBinding.getId());
                        assignmentNameToGrade.put(assignmentBinding.getAssignment().getName(),
                                lastResult
                                        .map(result -> new Grade(result.getScores(), result.getAssignmentBinding().getScores(), result.getStatus()))
                                        .orElse(new Grade()));
                    });
                    return new StudentGrades(subjectName, bindings.get(0).getCreated().getName(), assignmentNameToGrade);
                })
                .collect(toList());
    }

    public Optional<AssignmentResultDb> getLastResult(String studentLogin, Long assignmentBindingId) {
        return assignmentResultRepository.findByCreated_LoginAndAssignmentBinding_Id(studentLogin, assignmentBindingId)
                .stream()
                .max(Comparator.comparing(AssignmentResultDb::getId));
    }

    public List<FullAssignmentResultResponse> getFullNewAssignmentResultsFor(String teacher) {
        return assignmentResultRepository.findByStatusAndAssignmentBinding_Created_Login(Status.NEW, teacher)
                .stream()
                .map(assignmentResult -> {
                    String studentName = assignmentResult.getCreated().getName();
                    AssignmentBinding assignmentBinding = assignmentBindingConverter.convert(assignmentResult.getAssignmentBinding());
                    AssignmentResult newResult = assignmentResultConverter.convert(assignmentResult);
                    List<AssignmentResult> previousResults = assignmentResultRepository.findByCreated_LoginAndAssignmentBinding_Id(assignmentResult.getCreated().getLogin(), assignmentBinding.getId())
                            .stream()
                            .filter(assignmentResultDb -> !assignmentResultDb.getStatus().equals(Status.NEW))
                            .map(assignmentResultConverter::convert)
                            .collect(toList());
                    log.info("Found previous results: {}", previousResults);
                    return new FullAssignmentResultResponse(studentName, assignmentBinding, newResult, previousResults);
                })
                .collect(toList());
    }

    public void updateStatus(Long resultId, String comment, Integer scores, Status status) {
        AssignmentResultDb assignmentResult = assignmentResultRepository.findById(resultId).orElseThrow(() -> entityNotFound("assignment result", resultId));
        assignmentResult.setComment(comment);
        assignmentResult.setStatus(status);
        Optional.ofNullable(scores)
                .ifPresent(assignmentResult::setScores);
        assignmentResultRepository.save(assignmentResult);
        log.info("Updated!");
    }

    public Map<String, Map<AssignmentBinding, List<AssignmentResult>>> getAllResultsGroupedBySubjectFor(String teacherLogin, String studentLogin) {
        Set<Long> bindingIds = assignmentBindingRepository.findByCreated_Login(teacherLogin)
                .stream()
                .map(AssignmentBindingDb::getId)
                .collect(Collectors.toSet());

        return assignmentResultRepository.findByCreated_LoginAndAssignmentBinding_IdIn(studentLogin, bindingIds).stream()
                .collect(groupingBy(resultDb -> resultDb.getAssignmentBinding().getSubject()))
                .entrySet()
                .stream()
                .map(entry -> {
                    SubjectDb subjectDb = entry.getKey();
                    Map<AssignmentBinding, List<AssignmentResult>> map = entry.getValue().stream()
                            .collect(groupingBy(AssignmentResultDb::getAssignmentBinding))
                            .entrySet()
                            .stream()
                            .map(assignmentDbListEntry -> {
                                AssignmentBinding assignmentBinding = assignmentBindingConverter.convert(assignmentDbListEntry.getKey());
                                List<AssignmentResult> assignmentResults = assignmentDbListEntry.getValue()
                                        .stream()
                                        .map(assignmentResultConverter::convert)
                                        .sorted(Comparator.comparing(AssignmentResult::getId))
                                        .collect(toList());
                                return new AbstractMap.SimpleEntry<>(assignmentBinding, assignmentResults);
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    return new AbstractMap.SimpleEntry<>(subjectDb.getSubjectName(), map);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<AllGradesResponse> getByTeacher(String teacherLogin) {
        return convertToResponse(assignmentBindingRepository.findByCreated_Login(teacherLogin));
    }

    public List<AllGradesResponse> getByGroupForTeacher(Long groupId, String teacherLogin) {
        return convertToResponse(assignmentBindingRepository.findByCreated_LoginAndGroup_Id(teacherLogin, groupId));
    }

    public List<AllGradesResponse> getBySubjectForTeacher(Long subjectId, String teacherLogin) {
        return convertToResponse(assignmentBindingRepository.findByCreated_LoginAndSubject_Id(teacherLogin, subjectId));
    }

    private List<AllGradesResponse> convertToResponse(List<AssignmentBindingDb> assignmentBindingDbs) {
        return assignmentBindingDbs
                .stream()
                .collect(Collectors.groupingBy(AssignmentBindingDb::getSubject))
                .entrySet()
                .stream()
                .map(subjectDbListEntry -> {
                    SubjectDb subjectDb = subjectDbListEntry.getKey();
                    List<GroupGrades> groupGrades = subjectDbListEntry.getValue()
                            .stream()
                            .collect(groupingBy(AssignmentBindingDb::getGroup))
                            .entrySet()
                            .stream()
                            .map(groupDbListEntry -> {
                                GroupDb groupDb = groupDbListEntry.getKey();
                                List<AssignmentBindingDb> assignments = groupDbListEntry.getValue();
                                List<StudentAssignmentGradeResponse> grades = studentUserRepository.findByGroup_Id(groupDb.getId()).stream()
                                        .map(studentUserDb -> {
                                            Map<Long, Grade> gradeMap = assignments.stream()
                                                    .map(assignmentBindingDb ->
                                                            getLastResult(studentUserDb.getLogin(), assignmentBindingDb.getId())
                                                                    .map(resultDb -> Pair.of(resultDb.getAssignmentBinding().getId(),
                                                                            new Grade(resultDb.getScores(), resultDb.getAssignmentBinding().getScores(), resultDb.getStatus())))
                                                                    .orElse(Pair.of(assignmentBindingDb.getId(), new Grade(null, null, null)))
                                                    )
                                                    .collect(toMap(Pair::getFirst, Pair::getSecond));
                                            return new StudentAssignmentGradeResponse(studentUserDb.getName(), gradeMap);
                                        })
                                        .collect(toList());
                                return new GroupGrades(groupDb.getName(),
                                        assignments.stream()
                                                .map(assignmentBindingConverter::convert)
                                                .map(AssignmentBinding::getAssignment)
                                                .collect(toList()),
                                        grades);
                            })
                            .collect(toList());
                    return new AllGradesResponse(subjectDb.getSubjectName(), groupGrades);
                })
                .collect(toList());
    }
}
