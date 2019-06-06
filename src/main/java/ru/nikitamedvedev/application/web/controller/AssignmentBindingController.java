package ru.nikitamedvedev.application.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nikitamedvedev.application.service.AssignmentBindingService;
import ru.nikitamedvedev.application.service.dto.AssignmentBinding;
import ru.nikitamedvedev.application.web.dto.AssignmentBindingRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/assignment-binding")
@RequiredArgsConstructor
public class AssignmentBindingController {

    private final AssignmentBindingService assignmentBindingService;

    @PutMapping(path = "/add/{login}")
    public void addBinding(@PathVariable String login,
                           @RequestBody AssignmentBindingRequest request) {
        log.info("Received: {}", request);
        assignmentBindingService.bindAssignment(
                login,
                request.getAssignmentId(),
                request.getGroupId(),
                request.getSemesterId(),
                request.getSubjectId(),
                request.getScores(),
                request.getStartDate()
        );
    }

    @GetMapping(path = "/get-all/{login}")
    public List<AssignmentBinding> getAllBindings(@PathVariable String login){
        log.info("Returns all assignments for {}", login);
        return assignmentBindingService.getBindingsByCreator(login);
    }


}
