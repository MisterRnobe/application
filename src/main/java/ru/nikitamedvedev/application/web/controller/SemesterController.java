package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitamedvedev.application.service.SemesterService;
import ru.nikitamedvedev.application.service.dto.Semester;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/semester")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @GetMapping(path = "/get-all")
    public List<Semester> getAllSemesters() {
        return semesterService.getAllSemesters();
    }

    @GetMapping(path = "/get-current")
    public Semester getCurrent() {
        return semesterService.getCurrentSemester();
    }
}
