package ru.nikitamedvedev.application.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nikitamedvedev.application.hepler.PasswordGenerator;
import ru.nikitamedvedev.application.service.GroupService;
import ru.nikitamedvedev.application.service.SemesterService;
import ru.nikitamedvedev.application.service.SubjectService;
import ru.nikitamedvedev.application.service.UserService;

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
                                               SubjectService subjectService,
                                               GroupService groupService,
                                               SemesterService semesterService) {
        return (args) -> {
            subjectService.createSubject("Физика");
            subjectService.createSubject("Математика");
            subjectService.createSubject("История");
            subjectService.createSubject("РУсский язык");

            groupService.createGroup("Группа 1");
            groupService.createGroup("Группа 2");
            groupService.createGroup("Группа 3");

            semesterService.createSemester("Семестр 1");
            semesterService.createSemester("Семестр 2");

            userService.createTeacher("prepod", "Казимир Казимирович");
        };
    }
}
