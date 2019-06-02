package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.nikitamedvedev.application.persistence.*;
import ru.nikitamedvedev.application.persistence.dto.*;
import ru.nikitamedvedev.application.service.dto.AssignmentBinding;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.nikitamedvedev.application.hepler.ExceptionUtils.entityNotFound;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentBindingService {

    private final AssignmentBindingRepository bindingRepository;
    private final AssignmentRepository assignmentRepository;
    private final GroupRepository groupRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherUserRepository teacherUserRepository;

    private final Converter<AssignmentBindingDb, AssignmentBinding> converter;

    public void bindAssignment(String createdBy, Long assignmentId, Long groupId, Long semesterId, Long subjectId, Integer scores, LocalDate startDate) {
        val assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> entityNotFound("assignment", assignmentId));
        val group = groupRepository.findById(groupId).orElseThrow(() -> entityNotFound("group", groupId));
        val semester = semesterRepository.findById(semesterId).orElseThrow(() -> entityNotFound("semester", groupId));
        val subject = subjectRepository.findById(subjectId).orElseThrow(() -> entityNotFound("subject", subjectId));
        val teacher = teacherUserRepository.findById(createdBy).orElseThrow(() -> entityNotFound("teacher", createdBy));

        val assignmentBindingDb = new AssignmentBindingDb(null, assignment, group, semester, subject, teacher, startDate, scores);
        val save = bindingRepository.save(assignmentBindingDb);
        log.info("Saved: {}", save);
    }

    public List<AssignmentBinding> getBindingsByCreator(String creatorId) {
        return bindingRepository.findByCreated_Login(creatorId).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

}
