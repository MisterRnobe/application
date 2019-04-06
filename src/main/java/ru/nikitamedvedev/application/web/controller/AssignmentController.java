package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.core.service.AssignmentService;
import ru.nikitamedvedev.application.core.service.dto.UnprocessedWork;
import ru.nikitamedvedev.application.core.user.User;
import ru.nikitamedvedev.application.web.dto.CreateAssignmentRequest;
import ru.nikitamedvedev.application.web.model.FileResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(path = "/assignment")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping
    public String controlPanel() {
        return "assignment/main";
    }

    @GetMapping(path = "/add")
    public String getAddPage(Model model) {
        model.addAttribute("groups", assignmentService.getGroups());
        return "assignment/add";
    }

    @PostMapping(path = "/add")
    public String addAssignment(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name,
                                @RequestParam("maxScore") Integer maxScore,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime starts,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finishes,
                                @RequestParam List<String> groups) throws IOException {
        CreateAssignmentRequest request = CreateAssignmentRequest.builder()
                .name(name)
                .maxScore(maxScore)
                .starts(starts)
                .finishes(finishes)
                .groups(groups)
                .fileName(file.getOriginalFilename())
                .source(file.getBytes())
                .build();
        log.info("Received: {}", request);
        assignmentService.storeAssignment(request);
        return "assignment/main";
    }

    @GetMapping(path = "/new_works")
    public String getNewWorks(Model model) {
        model.addAttribute("groups", assignmentService.getGroups());
        return "assignment/new_work";
    }

    @GetMapping(path = "/result/{id}")
    public ResponseEntity<Resource> getResult(@PathVariable Long id) {
        FileResource resultResource = assignmentService.getResultResource(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resultResource.getFileName() + "\"")
                .body(resultResource.getResource());


    }

    @PostMapping(path = "/new_works")
    public String getNewWorks(@RequestParam Long groupId,
                              @RequestParam(required = false) Long userId,
                              Model model) {

        List<UnprocessedWork> newWorks = assignmentService.getNewWorks(groupId, userId);
        Map<Long, String> availableGroups = assignmentService.getGroups();
        List<User> availableUsers = assignmentService.getUsersInGroup(groupId);

        model.addAttribute("selectedGroup", availableGroups.get(groupId));

        model.addAttribute("groups", availableGroups);
        model.addAttribute("users", availableUsers);
        model.addAttribute("newWorks", newWorks);

        return "assignment/new_work";
    }

}
