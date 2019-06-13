package ru.nikitamedvedev.application.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.ImmutableMap;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nikitamedvedev.application.hepler.PasswordGenerator;
import ru.nikitamedvedev.application.service.*;
import ru.nikitamedvedev.application.service.dto.Role;

import java.util.Arrays;

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
    public Algorithm algorithm(@Value("${security.secret}") String secret) {
        return Algorithm.HMAC256(secret);
    }

    @Bean
    public SecurityConfig securityConfig() {
        return new SecurityConfig(
                ImmutableMap.of(
                        Role.STUDENT, Arrays.asList(
                                "/binding/get-by-student",
                                "/grades/get-current-grades",
                                "/result/add-result-assignment"
                        ),
                        Role.TEACHER, Arrays.asList(
                                "/assignment-binding/add",
                                "/assignment-binding/get-all",
                                "/assignment/create",
                                "/assignment/get-by-creator",
                                "/assignment/modify",
                                "/grades/get-all",
                                "/grades/get-by-group",
                                "/grades/get-by-subject",
                                "/group/get-by-teacher",
                                "/group/get-students-by-group",
                                "/result/get-new-results",
                                "/result/get-all-by-student",
                                "/result/update-status",
                                "/subject/bind",
                                "/subject/get-all"
                        ),
                        Role.ADMIN, Arrays.asList()
                )
                , Arrays.asList(
                "/group/get-all",
                "/actuator/health",
                "/semester/get-all",
                "/semester/get-current",
                "/users/authorize",
                "swagger",
                "v2/api-docs"
        )
        );
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService,
                                               ResultService resultService,
                                               SubjectService subjectService,
                                               SemesterService semesterService,
                                               AssignmentService assignmentService,
                                               AssignmentBindingService assignmentBindingService) {
        return (args) -> {
            val teacherLogin = "prepod";

            subjectService.createSubject("Физика");
            subjectService.createSubject("Математика");
            subjectService.createSubject("История");
            subjectService.createSubject("РУсский язык");

            userService.createGroupWithUsers("Группа 1", ImmutableMap.of("student", "Студент Первый", "student2", "Студент Второй"));
            userService.createGroupWithUsers("Группа 2", ImmutableMap.of("student3", "Студент Третий", "student4", "Студент Четвертый"));

            semesterService.createSemester("Семестр 1");
            semesterService.createSemester("Семестр 2");

            userService.createTeacher(teacherLogin, "Казимир Казимирович");

//            userService.bindTeacherToSubject(teacherLogin, 1L);
//            userService.bindTeacherToSubject(teacherLogin, 2L);
//            userService.bindTeacherToSubject(teacherLogin, 3L);
//
//            assignmentService.createAssignment(
//                    "Задание #1",
//                    "file.txt",
//                    "Content".getBytes(),
//                    teacherLogin
//            );
//
//            assignmentService.createAssignment(
//                    "Задание #2",
//                    "другой файл.txt",
//                    "Дороф".getBytes(),
//                    teacherLogin
//            );
//
//            assignmentService.createAssignment(
//                    "Задание #3",
//                    "Jeppa.txt",
//                    "jep-pa))0)".getBytes(),
//                    teacherLogin
//            );
//
//            assignmentBindingService.bindAssignment(
//                    teacherLogin,
//                    1L,
//                    1L,
//                    2L,
//                    1L,
//                    10,
//                    LocalDate.of(2019, 1, 1)
//            );
//
//            assignmentBindingService.bindAssignment(
//                    teacherLogin,
//                    2L,
//                    1L,
//                    2L,
//                    1L,
//                    8,
//                    LocalDate.of(2019, 5, 1)
//            );
//
//            assignmentBindingService.bindAssignment(
//                    teacherLogin,
//                    3L,
//                    2L,
//                    2L,
//                    1L,
//                    4,
//                    LocalDate.of(2019, 5, 1)
//            );
//
//            resultService.addAssignmentResult(
//                    1L,
//                    "student",
//                    "rabota.txt",
//                    "Lmao))".getBytes()
//            );
//
//            resultService.addAssignmentResult(
//                    1L,
//                    "student2",
//                    "lol.txt",
//                    "shreq is qeq".getBytes()
//            );
//
//            resultService.addAssignmentResult(
//                    2L,
//                    "student",
//                    "crud.txt",
//                    "Я крутой, так сказатб))".getBytes()
//            );
//
//            resultService.updateStatus(1L, "Переделать", 0, Status.NOT_ACCEPTED);
//            resultService.addAssignmentResult(
//                    1L,
//                    "student",
//                    "rabota_again.txt",
//                    "Шрек ита кек".getBytes()
//            );
//
//            resultService.addAssignmentResult(
//                    3L,
//                    "student3",
//                    "pew-pew)).txt",
//                    "Шрек ита кек".getBytes()
//            );
        };
    }
}
