package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.core.service.AssignmentService;
import ru.nikitamedvedev.application.web.dto.CreateAssignmentRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

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
                .build();
        log.info("Received: {}", request);
        assignmentService.storeAssignment(request, file.getBytes());
        return "assignment/main";
    }
}
