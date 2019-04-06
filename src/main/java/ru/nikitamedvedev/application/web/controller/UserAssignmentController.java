package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.core.service.AssignmentService;
import ru.nikitamedvedev.application.core.service.dto.AssignmentResult;
import ru.nikitamedvedev.application.core.user.User;
import ru.nikitamedvedev.application.web.model.FileResource;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping(path = "/work")
@RequiredArgsConstructor
public class UserAssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Resource> getWork(@PathVariable(name = "id") Long id) {
        FileResource assignment = assignmentService.getAssignmentSource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + assignment.getFileName() + "\"")
                .body(assignment.getResource());
    }

    @GetMapping(path = "/info")
    public String getCurrentWorksWithResults(Model model, Principal principal) {
        log.info("User {} requires its works", principal.getName());
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        List<AssignmentResult> assignmentResults = assignmentService.getAssignmentResults(user.getLogin());
        model.addAttribute("results", assignmentResults);
        model.addAttribute("user", user);
        return "work/info";
    }

    @PostMapping(path = "/upload/{id}")
    public String uploadAssignment(@PathVariable Long id,
                                 @RequestParam MultipartFile file,
                                 Principal principal) throws IOException {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        assignmentService.saveNewResult(id, user.getLogin(), file);
        return "redirect:/work/info";
    }
}
