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
import ru.nikitamedvedev.application.web.dto.FullAssignmentResultResponse;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
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

    public List<Pair<AssignmentResult, List<AssignmentResult>>> getAllNewResultFor(String teacher) {
        return assignmentResultRepository.findByStatusAndAssignmentBinding_Created_Login(Status.NEW, teacher)
                .stream()
                .map(assignmentResultConverter::convert)
                .map(result -> {
                    val created = result.getCreated();
                    val byCreated_login = assignmentResultRepository.findByCreated_Login(created)
                            .stream()
                            .filter(assignmentResultDb -> !assignmentResultDb.getStatus().equals(Status.NEW))
                            .map(assignmentResultConverter::convert)
                            .collect(toList());
                    return Pair.of(result, byCreated_login);
                })
                .collect(toList());
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
                                        .map(result -> new Grade(result.getScores(), result.getStatus()))
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

    //Subject name -> {Assignment name -> Result}
    //New = Last one
    public Map<String, Map<String, FullAssignmentResultResponse>> getAllResultsGroupedBySubjectFor(String teacherLogin, String studentLogin) {
        Set<Long> bindingIds = assignmentBindingRepository.findByCreated_Login(teacherLogin)
                .stream()
                .map(AssignmentBindingDb::getId)
                .collect(Collectors.toSet());

        assignmentResultRepository.findByCreated_LoginAndAssignmentBinding_IdIn(studentLogin, bindingIds).stream()
                .collect(groupingBy(resultDb -> resultDb.getAssignmentBinding().getSubject()))
                .entrySet()
                .stream()
                .map(entry->{
                    SubjectDb subjectDb = entry.getKey();
                    entry.getValue().stream()
                            .collect(groupingBy(assignmentResultDb -> assignmentResultDb.getAssignmentBinding().getAssignment()))
                            .entrySet()
                            .stream()
                            .map(assignmentDbListEntry -> {
                                AssignmentDb assignmentDb = assignmentDbListEntry.getKey();
                                List<AssignmentResult> assignmentResults = assignmentDbListEntry.getValue()
                                        .stream()
                                        .map(assignmentResultConverter::convert)
                                        .collect(toList());

                            })
                })
        return emptyMap();
    }
    /*
    assignmentBindingDb -> {
                    List<AssignmentResult> assignmentResults = assignmentResultRepository.findByCreated_LoginAndAssignmentBinding_Id(studentLogin, assignmentBindingDb.getId())
                            .stream()
                            .map(assignmentResultConverter::convert)
                            .sorted(Comparator.comparing(AssignmentResult::getAssignmentBindingId))
                            .collect(toList());
                    List<AssignmentResult> oldResults = new ArrayList<>();
                    AssignmentResult last = null;
                    if (!assignmentResults.isEmpty()) {
                        last = assignmentResults.get(assignmentResults.size() - 1);
                        for (int i = 0; i < assignmentResults.size() - 1; i++) {
                            oldResults.add(assignmentResults.get(i));
                        }
                    }


                }
     */
}
