package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nikitamedvedev.application.service.AssignmentBindingService;
import ru.nikitamedvedev.application.service.dto.AssignmentTestBinding;
import ru.nikitamedvedev.application.web.dto.AssignmentTestBindingRequest;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/assignment-test-binding")
@RequiredArgsConstructor
public class AssignmentTestBindingController {

    private final AssignmentBindingService assignmentBindingService;

    @PutMapping(path = "/add/{login}")
    public void addBinding(@PathVariable String login,
                           @Valid @RequestBody AssignmentTestBindingRequest request) {
        log.info("Received: {}", request);
        assignmentBindingService.bindAssignmentTest(
                login,
                request.getAssignmentTestId(),
                request.getGroupId(),
                request.getSemesterId(),
                request.getSubjectId(),
                request.getScores(),
                request.getStartDate(),
                request.getDuration()
        );
    }

    @GetMapping(path = "/get-all/{login}")
    public List<AssignmentTestBinding> getAllBindings(@PathVariable String login) {
        log.info("Returns all assignments for {}", login);
        return assignmentBindingService.getTestBindingsByCreator(login);
    }

}
