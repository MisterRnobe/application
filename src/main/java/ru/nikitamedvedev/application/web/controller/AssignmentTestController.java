package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nikitamedvedev.application.service.AssignmentTestService;
import ru.nikitamedvedev.application.service.dto.AssignmentTest;
import ru.nikitamedvedev.application.web.dto.CreateAssignmentTestRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/assignment-test")
@RequiredArgsConstructor
public class AssignmentTestController {

    private final AssignmentTestService assignmentTestService;

    @PutMapping(path = "/create/{login}")
    public void createAssignmentTest(@PathVariable String login,
                                     @RequestBody CreateAssignmentTestRequest request) {
        log.info("Received create test assignment request: {}", request);
        assignmentTestService.createAssignment(request.getName(), request.getQuestions(), request.getScores(), login);
        log.info("Create test assignment request was proceeded");
    }

    @PostMapping(path = "/modify/{id}")
    public void updateAssignmentTest(@PathVariable Long id,
                                     @RequestBody CreateAssignmentTestRequest request) {
        log.info("Received modify {} test assignment request: {}", id, request);
        assignmentTestService.modifyAssignment(id, request.getName(), request.getQuestions());
        log.info("Modify test assignment request was proceeded");
    }

    @GetMapping(path = "/get-by-creator/{login}")
    public List<AssignmentTest> getTestAssignmentByCreator(@PathVariable String login) {
        log.info("Received get by creator request for {}", login);
        return assignmentTestService.getByCreator(login);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void getTestAssignmentByCreator(@PathVariable Long id) {
        log.info("Received delete test assignment {}", id);
        assignmentTestService.deleteAssignment(id);
        log.info("Deleted");
    }
}
