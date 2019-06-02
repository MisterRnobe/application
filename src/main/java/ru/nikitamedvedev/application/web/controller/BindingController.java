package ru.nikitamedvedev.application.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nikitamedvedev.application.service.AssignmentBindingService;
import ru.nikitamedvedev.application.web.dto.AddBindingRequest;

@Slf4j
@RestController
@RequestMapping(path = "/assignment-binding")
@RequiredArgsConstructor
public class BindingController {

    private final AssignmentBindingService assignmentBindingService;

    @PutMapping(path = "/add/{login}")
    public void addBinding(@PathVariable String login,
                           @RequestBody AddBindingRequest request) {
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


}
