package ru.nikitamedvedev.application.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitamedvedev.application.service.AssignmentBindingService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/binding")
@RequiredArgsConstructor
public class BindingController {

    private final AssignmentBindingService assignmentBindingService;

    @GetMapping(path = "/get-by-group/{groupId}")
    public Map<String, List<Object>> getAllBindingsByGroup(@PathVariable Long groupId) {
        return assignmentBindingService.getAllBindingsByGroup(groupId);
    }
}
