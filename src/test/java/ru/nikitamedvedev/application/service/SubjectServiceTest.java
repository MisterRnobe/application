package ru.nikitamedvedev.application.service;

import com.google.common.collect.ImmutableSet;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nikitamedvedev.application.Helper;
import ru.nikitamedvedev.application.persistence.SubjectRepository;
import ru.nikitamedvedev.application.persistence.TeacherUserRepository;
import ru.nikitamedvedev.application.persistence.dto.SubjectDb;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;
import ru.nikitamedvedev.application.service.dto.Subject;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@DirtiesContext
@RunWith(SpringRunner.class)
public class SubjectServiceTest extends Helper {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherUserRepository teacherUserRepository;
    @Autowired
    private SubjectService subjectService;

    private final static String NAME1 = "NAME1";
    private final static String NAME2 = "NAME2";
    private final static String NAME3 = "NAME3";

    private final static String TEACHER1 = "prepod1";
    private final static String TEACHER2 = "prepod2";

    @Before
    public void init() {
//        teacherUserRepository.deleteAll();
//        subjectRepository.deleteAll();
//
//        val subject1 = subjectRepository.save(new SubjectDb(null, NAME1));
//        val subject2 = subjectRepository.save(new SubjectDb(null, NAME2));
//        val subject3 = subjectRepository.save(new SubjectDb(null, NAME3));
//
//        teacherUserRepository.save(new TeacherUserDb(TEACHER1, "", "", Arrays.asList(subject1, subject2)));
//        teacherUserRepository.save(new TeacherUserDb(TEACHER2, "", "", Collections.emptyList()));
    }

    @Test
    @SneakyThrows
    public void shouldFindAllNotBoundSubjects() {
//        val actual = subjectService.getAllNotBoundSubjects(TEACHER2);
//
//        assertEquals(3, actual.size());
//        assertEquals(ImmutableSet.of(NAME1, NAME2, NAME3), actual.stream().map(Subject::getName).collect(Collectors.toSet()));
    }

    @Test
    @SneakyThrows
    public void shouldFindAllBoundSubjects() {
//        val actual = subjectService.getAllBoundSubjects(TEACHER1);
//
//        assertEquals(2, actual.size());
//        assertEquals(ImmutableSet.of(NAME1, NAME2), actual.stream().map(Subject::getName).collect(Collectors.toSet()));
    }

    @Test
    @SneakyThrows
    public void shouldGetAllNotBoundSubjects() {
    }

    @Test
    @SneakyThrows
    public void shouldGetAllBoundSubjects() {
    }

    @Test
    @SneakyThrows
    public void shouldCreateSubject() {
    }
}