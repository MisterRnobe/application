package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitamedvedev.application.service.ResultService;
import ru.nikitamedvedev.application.service.dto.StudentGrades;
import ru.nikitamedvedev.application.web.dto.AllGradesResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/grades")
@RequiredArgsConstructor
public class GradesController {

    private final ResultService resultService;

    @GetMapping(path = "/get-current-grades/{login}")
    public List<StudentGrades> getCurrentGradesForStudent(@PathVariable String login) {
        return resultService.getAssignmentGradesForStudent(login);
    }

    @GetMapping(path = "/get-all/{teacherLogin}")
    public List<AllGradesResponse> getAllGradesByTeacher(@PathVariable String teacherLogin) {
        return resultService.getByTeacher(teacherLogin);
    }

    @GetMapping(path = "/get-by-group/{groupId}/{teacherLogin}")
    public List<AllGradesResponse> getAllGradesByTeacherAndGroup(@PathVariable Long groupId,
                                                                 @PathVariable String teacherLogin) {
        return resultService.getByGroupForTeacher(groupId, teacherLogin);
    }

    @GetMapping(path = "/get-by-subject/{subjectId}/{teacherLogin}")
    public List<AllGradesResponse> getAllGradesByTeacherAndSubject(@PathVariable Long subjectId,
                                                                   @PathVariable String teacherLogin) {
        return resultService.getBySubjectForTeacher(subjectId, teacherLogin);
    }
}
