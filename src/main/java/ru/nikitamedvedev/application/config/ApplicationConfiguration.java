package ru.nikitamedvedev.application.config;

import com.google.common.collect.ImmutableMap;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nikitamedvedev.application.hepler.PasswordGenerator;
import ru.nikitamedvedev.application.service.*;
import ru.nikitamedvedev.application.service.dto.Status;

import java.time.LocalDate;

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
        };
    }
}
