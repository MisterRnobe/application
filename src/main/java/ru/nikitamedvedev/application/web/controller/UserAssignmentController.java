package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nikitamedvedev.application.core.service.AssignmentService;
import ru.nikitamedvedev.application.core.service.dto.AssignmentResult;
import ru.nikitamedvedev.application.core.user.User;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(path = "/templates/work")
@RequiredArgsConstructor
public class UserAssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping(path = "/info")
    public String getCurrentWorksWithResults(Model model, Principal principal) {
        log.info("User {} requires its works", principal.getName());
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        List<AssignmentResult> assignmentResults = assignmentService.getAssignmentResults(user.getLogin());
        model.addAttribute("results", assignmentResults);
        model.addAttribute("user", user);
        return "work/info";
    }
}
