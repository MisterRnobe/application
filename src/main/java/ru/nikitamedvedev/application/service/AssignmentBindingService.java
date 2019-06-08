package ru.nikitamedvedev.application.service;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.nikitamedvedev.application.persistence.*;
import ru.nikitamedvedev.application.persistence.dto.AssignmentBindingDb;
import ru.nikitamedvedev.application.persistence.dto.AssignmentTestBindingDb;
import ru.nikitamedvedev.application.persistence.dto.StudentUserDb;
import ru.nikitamedvedev.application.service.dto.AssignmentBinding;
import ru.nikitamedvedev.application.service.dto.AssignmentTestBinding;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.nikitamedvedev.application.hepler.ExceptionUtils.entityNotFound;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentBindingService {

    private final AssignmentBindingRepository assignmentBindingRepository;
    private final AssignmentTestBindingRepository assignmentTestBindingRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentTestRepository assignmentTestRepository;
    private final GroupRepository groupRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final StudentUserRepository studentUserRepository;

    private final Converter<AssignmentBindingDb, AssignmentBinding> assignmentBindingConverter;
    private final Converter<AssignmentTestBindingDb, AssignmentTestBinding> assignmentTestBindingConverter;

    public void bindAssignment(String createdBy, Long assignmentId, Long groupId, Long semesterId, Long subjectId, Integer scores, LocalDate startDate) {
        val assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> entityNotFound("assignment", assignmentId));
        val group = groupRepository.findById(groupId).orElseThrow(() -> entityNotFound("group", groupId));
        val semester = semesterRepository.findById(semesterId).orElseThrow(() -> entityNotFound("semester", groupId));
        val subject = subjectRepository.findById(subjectId).orElseThrow(() -> entityNotFound("subject", subjectId));
        val teacher = teacherUserRepository.findById(createdBy).orElseThrow(() -> entityNotFound("teacher", createdBy));

        val assignmentBindingDb = new AssignmentBindingDb(null, assignment, group, semester, subject, teacher, startDate, scores);
        val save = assignmentBindingRepository.save(assignmentBindingDb);
        log.info("Saved assignment binding: {}", save);
    }

    public void bindAssignmentTest(String createdBy,
                                   Long testAssignmentId,
                                   Long groupId,
                                   Long semesterId,
                                   Long subjectId,
                                   Integer scores,
                                   LocalDate startDate,
                                   Integer duration) {
        val assignment = assignmentTestRepository.findById(testAssignmentId).orElseThrow(() -> entityNotFound("assignment-test", testAssignmentId));
        val group = groupRepository.findById(groupId).orElseThrow(() -> entityNotFound("group", groupId));
        val semester = semesterRepository.findById(semesterId).orElseThrow(() -> entityNotFound("semester", groupId));
        val subject = subjectRepository.findById(subjectId).orElseThrow(() -> entityNotFound("subject", subjectId));
        val teacher = teacherUserRepository.findById(createdBy).orElseThrow(() -> entityNotFound("teacher", createdBy));

        val assignmentTestBindingDb = new AssignmentTestBindingDb(null, assignment, group, semester, subject, teacher, startDate, scores, duration);
        val save = assignmentTestBindingRepository.save(assignmentTestBindingDb);
        log.info("Saved assignment test binding: {}", save);

    }

    public List<AssignmentTestBinding> getTestBindingsByCreator(String login) {
        return assignmentTestBindingRepository.findByCreated_Login(login).stream()
                .map(assignmentTestBindingConverter::convert)
                .collect(Collectors.toList());
    }

    public List<AssignmentBinding> getBindingsByCreator(String creatorId) {
        return assignmentBindingRepository.findByCreated_Login(creatorId).stream()
                .map(assignmentBindingConverter::convert)
                .collect(Collectors.toList());
    }

    public Map<String, List<Object>> getAllBindingsByGroupAndSemester(Long groupId, Long semesterId) {
        Map<String, List<AssignmentTestBindingDb>> assignmentTests = assignmentTestBindingRepository.findByGroup_IdAndSemester_Id(groupId, semesterId)
                .stream()
                .collect(Collectors.groupingBy(assignmentTestBindingDb -> assignmentTestBindingDb.getSubject().getSubjectName()));
        Map<String, List<AssignmentBindingDb>> assignments = assignmentBindingRepository.findByGroup_IdAndSemester_Id(groupId, semesterId)
                .stream()
                .collect(Collectors.groupingBy(assignmentTestBindingDb -> assignmentTestBindingDb.getSubject().getSubjectName()));

        return Sets.union(assignmentTests.keySet(), assignments.keySet())
                .immutableCopy()
                .stream()
                .collect(Collectors.toMap(Function.identity(), subject -> {
                    List<? extends Object> assignmentTestBindings = Optional.ofNullable(assignmentTests.get(subject))
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(assignmentTestBindingConverter::convert)
                            .collect(Collectors.toList());
                    List<? extends Object> assignmentBindings = Optional.ofNullable(assignments.get(subject))
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(assignmentBindingConverter::convert)
                            .collect(Collectors.toList());
                    List<Object> objects = new ArrayList<>();
                    objects.addAll(assignmentBindings);
                    objects.addAll(assignmentTestBindings);
                    return objects;
                }));
    }

    public Map<String, List<AssignmentBinding>> getAssignmentBindingsByStudentAndSemester(String login, Long semesterId) {
        StudentUserDb student = studentUserRepository.findById(login).orElseThrow(() -> entityNotFound("student", login));

        return assignmentBindingRepository.findByGroup_IdAndSemester_Id(student.getGroup().getId(), semesterId)
                .stream()
                .map(assignmentBindingConverter::convert)
                .collect(Collectors.groupingBy(assignmentTestBinding -> assignmentTestBinding.getSubject().getName()));
    }
}
