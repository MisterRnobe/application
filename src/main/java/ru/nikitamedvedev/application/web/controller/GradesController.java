package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitamedvedev.application.service.ResultService;

@Slf4j
@RestController
@RequestMapping(path = "/grades")
@RequiredArgsConstructor
public class GradesController {

    private final ResultService resultService;

    @GetMapping(path = "/get-current-grades/{login}")
    public Object getCurrentGradesForStudent(@PathVariable String login) {
        val gradesForStudent = resultService.getAssignmentGradesForStudent(login);
        return gradesForStudent;
    }
}
