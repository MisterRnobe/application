package ru.nikitamedvedev.application.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitamedvedev.application.service.AssignmentBindingService;
import ru.nikitamedvedev.application.service.ResultService;
import ru.nikitamedvedev.application.service.dto.AssignmentBinding;
import ru.nikitamedvedev.application.service.dto.AssignmentResult;
import ru.nikitamedvedev.application.web.dto.AssignmentBindingCommonResponse;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/binding")
@RequiredArgsConstructor
public class BindingController {

    private final AssignmentBindingService assignmentBindingService;
    private final ResultService resultService;

    @GetMapping(path = "/get-by-student/{semesterId}/{login}")
    public Map<String, List<AssignmentBindingCommonResponse>> getAllBindingsBySemesterAndStudent(@PathVariable Long semesterId,
                                                                                                 @PathVariable String login) {
        val assignmentBindings = assignmentBindingService.getAssignmentBindingsByStudentAndSemester(login, semesterId);
        log.info("Found assignment bindings: {}", assignmentBindings);
        val assignmentResultsForUser = resultService.findAssignmentResultsForUser(login);
        log.info("Found assignment results: {}", assignmentResultsForUser);
        Map<String, List<AssignmentBindingCommonResponse>> responseMap = assignmentBindings
                .entrySet()
                .stream()
                .map(entry -> {
                    val response = convertToResponse(entry.getValue(), assignmentResultsForUser);
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), response);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        log.info("Merged and got response map: {}", responseMap);
        return responseMap;
    }

    private List<AssignmentBindingCommonResponse> convertToResponse(List<AssignmentBinding> assignmentBindingList,
                                                                    Map<Long, List<AssignmentResult>> resultMap) {
        return assignmentBindingList.stream()
                .map(assignmentBinding -> {
                    val response = new AssignmentBindingCommonResponse();
                    response.setId(assignmentBinding.getId());
                    response.setName(assignmentBinding.getAssignment().getName());
                    response.setCreated(assignmentBinding.getCreated().getName());
                    response.setFileId(assignmentBinding.getAssignment().getFileId());
//                    response.setSemester(assignmentBinding.getSemester().getName());
                    response.setScores(assignmentBinding.getScores());
                    response.setStarts(assignmentBinding.getStarts());
                    response.setResults(Optional.ofNullable(resultMap.get(assignmentBinding.getId())).orElse(Collections.emptyList()));
                    return response;
                })
                .collect(Collectors.toList());

    }
}
