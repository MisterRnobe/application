package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.service.ResultService;
import ru.nikitamedvedev.application.service.dto.AssignmentBinding;
import ru.nikitamedvedev.application.service.dto.AssignmentResult;
import ru.nikitamedvedev.application.web.dto.AssignmentToResultsResponse;
import ru.nikitamedvedev.application.web.dto.FullAssignmentResultResponse;
import ru.nikitamedvedev.application.web.dto.StudentResultResponse;
import ru.nikitamedvedev.application.web.dto.UpdateResultRequest;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @SneakyThrows
    @PostMapping(path = "/add-result-assignment/{assignmentId}/{login}")
    public void addAssignmentResultForStudent(@PathVariable Long assignmentId,
                                              @PathVariable String login,
                                              @RequestParam MultipartFile file) {
        resultService.addAssignmentResult(
                assignmentId,
                login,
                file.getOriginalFilename(),
                file.getBytes());
        log.info("Result saved!");
    }

    @GetMapping(path = "/get-new-results/{teacher}")
    public List<FullAssignmentResultResponse> getAllNewResults(@PathVariable String teacher) {
        return resultService.getFullNewAssignmentResultsFor(teacher);
    }

    @PostMapping(path = "/update-status/{resultId}")
    public void updateResultStatus(@PathVariable Long resultId,
                                   @RequestBody UpdateResultRequest request) {
        resultService.updateStatus(resultId, request.getComment(), request.getScores(), request.getStatus());
    }

    @GetMapping(path = "/get-all-by-student/{teacherLogin}/{studentLogin}")
    public List<StudentResultResponse> getByStudent(@PathVariable String teacherLogin,
                                                    @PathVariable String studentLogin) {
        return resultService.getAllResultsGroupedBySubjectFor(teacherLogin, studentLogin).entrySet()
                .stream()
                .map(entry -> {
                    String subjectName = entry.getKey();
                    List<AssignmentToResultsResponse> assignmentToResultsResponses = entry.getValue().entrySet()
                            .stream()
                            .map(assignmentToResults -> {
                                AssignmentBinding assignmentBinding = assignmentToResults.getKey();
                                List<AssignmentResult> results = assignmentToResults.getValue();
                                return new AssignmentToResultsResponse(assignmentBinding, results);
                            })
                            .collect(Collectors.toList());
                    return new StudentResultResponse(subjectName, assignmentToResultsResponses);
                })
                .collect(Collectors.toList());
    }


}
