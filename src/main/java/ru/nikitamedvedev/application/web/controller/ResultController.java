package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.service.ResultService;
import ru.nikitamedvedev.application.web.dto.FullAssignmentResultResponse;

import java.util.List;

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
//        List<AssignmentResultResponse> resultListMap = resultService.getAllNewResultFor(teacher)
//                .stream()
//                .map(pair -> new AssignmentResultResponse(pair.getFirst(), pair.getSecond()))
//                .collect(Collectors.toList());
    }

    @PostMapping(path = "/update-status/{resultId}")
    public void updateResultStatus(@PathVariable Long resultId,
                                   UpdateResultRequest request) {
        resultService.updateStatus(resultId, request.getComment(), request.getScores(), request.getStatus());

    }

}
