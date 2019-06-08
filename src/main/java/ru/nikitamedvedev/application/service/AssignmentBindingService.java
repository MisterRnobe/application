package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.nikitamedvedev.application.persistence.*;
import ru.nikitamedvedev.application.persistence.dto.AssignmentBindingDb;
import ru.nikitamedvedev.application.persistence.dto.StudentUserDb;
import ru.nikitamedvedev.application.service.dto.AssignmentBinding;

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
    private final AssignmentRepository assignmentRepository;
    private final GroupRepository groupRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final StudentUserRepository studentUserRepository;

    private final Converter<AssignmentBindingDb, AssignmentBinding> assignmentBindingConverter;

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

    public List<AssignmentBinding> getBindingsByCreator(String creatorId) {
        return assignmentBindingRepository.findByCreated_Login(creatorId).stream()
                .map(assignmentBindingConverter::convert)
                .collect(Collectors.toList());
    }

    public Map<String, List<AssignmentBinding>> getAssignmentBindingsByStudentAndSemester(String login, Long semesterId) {
        StudentUserDb student = studentUserRepository.findById(login).orElseThrow(() -> entityNotFound("student", login));

        return assignmentBindingRepository.findByGroup_IdAndSemester_Id(student.getGroup().getId(), semesterId)
                .stream()
                .map(assignmentBindingConverter::convert)
                .collect(Collectors.groupingBy(assignmentBinding -> assignmentBinding.getSubject().getName()));
    }
}
