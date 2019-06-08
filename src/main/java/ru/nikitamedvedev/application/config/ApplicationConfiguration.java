package ru.nikitamedvedev.application.config;

import com.google.common.collect.ImmutableMap;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nikitamedvedev.application.hepler.PasswordGenerator;
import ru.nikitamedvedev.application.service.*;
import ru.nikitamedvedev.application.service.dto.Question;
import ru.nikitamedvedev.application.service.dto.Status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useUpper(true)
                .usePunctuation(false)
                .useLower(true)
                .build();
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService,
                                               ResultService resultService,
                                               SubjectService subjectService,
                                               SemesterService semesterService,
                                               AssignmentService assignmentService,
                                               AssignmentTestService assignmentTestService,
                                               AssignmentBindingService assignmentBindingService) {
        return (args) -> {
            val teacherLogin = "prepod";

            subjectService.createSubject("Физика");
            subjectService.createSubject("Математика");
            subjectService.createSubject("История");
            subjectService.createSubject("РУсский язык");

            userService.createGroupWithUsers("Группа 1", ImmutableMap.of("student", "Студент Студентов", "student1", "Студент Первый"));

            semesterService.createSemester("Семестр 1");
            semesterService.createSemester("Семестр 2");

            userService.createTeacher(teacherLogin, "Казимир Казимирович");

            userService.bindTeacherToSubject(teacherLogin, 1L);
            userService.bindTeacherToSubject(teacherLogin, 2L);
            userService.bindTeacherToSubject(teacherLogin, 3L);

            assignmentTestService.createAssignment(
                    "Test #1",
                    Arrays.asList(
                            new Question("Question #1", Arrays.asList("Bad 1", "Bad 2"), Collections.singletonList("Ok 1")),
                            new Question("Question #2", Arrays.asList("Bad 3", "Bad 4"), Collections.singletonList("Ok 2")),
                            new Question("Question #3", Arrays.asList("Bad 5", "Bad 6"), Collections.singletonList("Ok 3"))
                    ),
                    teacherLogin);
            assignmentService.createAssignment(
                    "Задание #1",
                    "file.txt",
                    "Content".getBytes(),
                    teacherLogin
            );

            assignmentService.createAssignment(
                    "Задание #2",
                    "другой файл.txt",
                    "Дороф".getBytes(),
                    teacherLogin
            );

            assignmentBindingService.bindAssignment(
                    teacherLogin,
                    1L,
                    1L,
                    2L,
                    1L,
                    10,
                    LocalDate.of(2019, 1, 1)
            );

            assignmentBindingService.bindAssignment(
                    teacherLogin,
                    2L,
                    1L,
                    2L,
                    1L,
                    8,
                    LocalDate.of(2019, 5, 1)
            );

            assignmentBindingService.bindAssignmentTest(
                    teacherLogin,
                    1L,
                    1L,
                    2L,
                    3L,
                    12,
                    LocalDate.of(2019, 5, 5),
                    10
            );
            resultService.addAssignmentResult(
                    1L,
                    "student",
                    "rabota.txt",
                    "Lmao))".getBytes()
            );

            resultService.addAssignmentResult(
                    1L,
                    "student1",
                    "lol.txt",
                    "shreq is qeq".getBytes()
            );

            resultService.addAssignmentResult(
                    2L,
                    "student",
                    "crud.txt",
                    "Я крутой, так сказатб))".getBytes()
            );

            resultService.updateStatus(1L, "Переделать", 0, Status.NOT_ACCEPTED);
            resultService.addAssignmentResult(
                    1L,
                    "student",
                    "rabota_again.txt",
                    "Шрек ита кек".getBytes()
            );
//            assignmentTestService.createAssignment(
//                    "Test #2",
//                    Arrays.asList(
//                            new Question("Question #4", Arrays.asList("Bad 7", "Bad 8"), Collections.singletonList("Ok 4")),
//                            new Question("Question #5", Arrays.asList("Bad 9", "Bad 10"), Collections.singletonList("Ok 5")),
//                            new Question("Question #6", Arrays.asList("Bad 11", "Bad 12"), Collections.singletonList("Ok 6"))
//                    ),
//                    2,
//                    teacherLogin);

        };
    }
}
