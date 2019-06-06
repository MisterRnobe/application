package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.GroupRepository;
import ru.nikitamedvedev.application.persistence.StudentUserRepository;
import ru.nikitamedvedev.application.persistence.SubjectRepository;
import ru.nikitamedvedev.application.persistence.TeacherUserRepository;
import ru.nikitamedvedev.application.persistence.dto.GroupDb;
import ru.nikitamedvedev.application.persistence.dto.StudentUserDb;
import ru.nikitamedvedev.application.persistence.dto.SubjectDb;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;
import ru.nikitamedvedev.application.hepler.PasswordGenerator;
import ru.nikitamedvedev.application.service.converter.TeacherUserDbToTeacherUserConverter;
import ru.nikitamedvedev.application.service.dto.TeacherUser;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {

    private final TeacherUserRepository teacherUserRepository;
    private final StudentUserRepository studentUserRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;
    //    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;
    private final TeacherUserDbToTeacherUserConverter converter;

    public List<Account> createGroupWithUsers(String groupName, Map<String, String> loginsToName) {
        val saved = groupRepository.save(new GroupDb(null, groupName, Collections.emptyList()));

        List<StudentUserDb> collect = loginsToName.entrySet().stream()
                .map(entry -> {
                    return new StudentUserDb(entry.getKey(), "", entry.getValue(), saved);
                })
                .collect(Collectors.toList());
        studentUserRepository.saveAll(collect);
        return null;
    }

    //User -> Password
    public Pair<TeacherUser, String> createTeacher(String login, String name) {
        val teacherUserDb = new TeacherUserDb();
        teacherUserDb.setLogin(login);
        teacherUserDb.setName(name);
        teacherUserDb.setPassword(passwordGenerator.generate(8));

        val save = teacherUserRepository.save(teacherUserDb);
        log.info("saved: {}", save);
        return Pair.of(converter.convert(save), save.getPassword());
    }

    public void bindTeacherToSubject(String login, Long id) {
        val subjectDb = subjectRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Subject with id " + id + " was not found"));
        val teacherUserDb = teacherUserRepository.findById(login).orElseThrow(() -> new NoSuchElementException("Teacher with login " + login + " was not found"));
        List<SubjectDb> subjectDbs = Optional.ofNullable(teacherUserDb.getSubjects()).orElseGet(() -> {
            val subjects = new ArrayList<SubjectDb>();
            teacherUserDb.setSubjects(subjects);
            return subjects;
        });
        subjectDbs.add(subjectDb);

        val save = teacherUserRepository.save(teacherUserDb);
        log.info("Saved: {}", save);
    }
}
