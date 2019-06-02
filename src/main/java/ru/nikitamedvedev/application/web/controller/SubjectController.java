package ru.nikitamedvedev.application.web.controller;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.nikitamedvedev.application.service.SubjectService;
import org.springframework.web.bind.annotation.*;
import ru.nikitamedvedev.application.service.UserService;
import ru.nikitamedvedev.application.service.dto.Subject;
import ru.nikitamedvedev.application.web.dto.CreateSubjectRequest;
import ru.nikitamedvedev.application.web.enums.Availability;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final UserService userService;

    @PutMapping(path = "/create")
    public void createSubject(@RequestBody CreateSubjectRequest request) {
        log.info("Create subject request: {}", request);
        subjectService.createSubject(request.getName());
        log.info("Done");
    }

    @GetMapping(path = "/get-all/{login}")
    public Map<Availability, List<Subject>> getAllSubjects(@PathVariable String login) {
        log.info("Get all subjects request: {}", login);
        val allBoundSubjects = subjectService.getAllBoundSubjects(login);
        val allNotBoundSubjects = subjectService.getAllNotBoundSubjects(login);
        return ImmutableMap.of(Availability.available, allNotBoundSubjects, Availability.current, allBoundSubjects);
    }

    @PostMapping(path = "/bind/{login}/{id}")
    public void bindToTeacher(@PathVariable String login,
                              @PathVariable Long id) {
        log.info("Binding {} to subject with id: {}", login, id);
        userService.bindTeacherToSubject(login, id);
        log.info("Done");
    }
}
