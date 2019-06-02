package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.SubjectRepository;
import ru.nikitamedvedev.application.persistence.TeacherUserRepository;
import ru.nikitamedvedev.application.persistence.dto.SubjectDb;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;
import ru.nikitamedvedev.application.service.dto.Subject;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherUserRepository teacherUserRepository;

    public List<Subject> getAllNotBoundSubjects(String login) {
        Set<Long> subjectIds = teacherUserRepository.findById(login)
                .map(TeacherUserDb::getSubjects)
                .map(subjectDbs -> subjectDbs.stream().map(SubjectDb::getId).collect(toSet()))
                .map(set -> set.isEmpty() ? Collections.singleton(-1L) : set)
                .orElseThrow(() -> new NoSuchElementException("Teacher with login " + login + " not found!"));
        return subjectRepository.findByIdIsNotIn(subjectIds).stream().map(this::convert).collect(Collectors.toList());
    }

    public List<Subject> getAllBoundSubjects(String login) {
        Set<Long> subjectIds = teacherUserRepository.findById(login)
                .map(TeacherUserDb::getSubjects)
                .map(subjectDbs -> subjectDbs.stream().map(SubjectDb::getId).collect(toSet()))
                .orElseThrow(() -> new NoSuchElementException("Teacher with login " + login + " not found!"));
        return subjectRepository.findByIdIn(subjectIds).stream().map(this::convert).collect(Collectors.toList());

    }

    public void createSubject(String name) {
        val save = subjectRepository.save(new SubjectDb(null, name));
        log.info("Saved subject: {}", save);
    }

    private Subject convert(SubjectDb subjectDb) {
        return new Subject(subjectDb.getId(), subjectDb.getSubjectName());
    }
}
