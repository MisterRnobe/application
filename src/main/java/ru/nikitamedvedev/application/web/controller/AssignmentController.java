package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.core.service.AssignmentService;
import ru.nikitamedvedev.application.core.service.dto.Assignment;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/assignment")
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('ADMIN')")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @SneakyThrows
    @PostMapping(path = "/create")
    public void createAssignment(@RequestParam("file") MultipartFile file,
                                 @RequestParam("name") String name,
                                 @RequestParam("postedBy") String postedBy) {
        log.info("Received add assignment response: {} with filename {} by {}", name, file.getOriginalFilename(), postedBy);
        assignmentService.createAssignment(name, file.getOriginalFilename(), file.getBytes(), postedBy);
        log.info("Assignment stored processed");
    }

    @GetMapping(path = "/get-by-creator/{login}")
    public List<Assignment> getAllBelongingTo(@PathVariable String login) {
        log.info("Received get assignment by creator request: {}", login);
        val assignments = assignmentService.getAssignmentsByCreator(login);
        log.info("Found assignment. Returning...");
        return assignments;
    }

    @PostMapping(path = "/modify/{id}")
    public void updateAssignment(@RequestParam(value = "file", required = false) MultipartFile file,
                                 @RequestParam(value = "name", required = false) String name,
                                 @PathVariable Long id) {
        log.info("Updating assignment: {}", id);
        val fileName = Optional.ofNullable(file)
                .map(MultipartFile::getOriginalFilename)
                .orElse(null);
        val fileContent = Optional.ofNullable(file)
                .map(f -> {
                    byte[] bytes = null;
                    try {
                        bytes = f.getBytes();
                    } catch (IOException e) {
                        log.error("Error occurred when reading file...", e);
                    }
                    return bytes;
                })
                .orElse(null);

        assignmentService.updateAssignment(id, name, fileName, fileContent);
        log.info("Assignment updated");
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteAssignment(@PathVariable Long id) {
        log.info("Deleting assignment: {}", id);
        assignmentService.deleteAssignment(id);
        log.info("Assignment removed");
    }
}
