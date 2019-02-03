package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nikitamedvedev.application.core.user.User;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class ProfileController {

    @GetMapping(path = "/profile")
    public String getProfile(Model model, Principal principal) {
        log.info("User is : {}", principal.getName());
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        model.addAttribute("name", user.getFullName());
        model.addAttribute("groupName", user.getGroupName());
        model.addAttribute("role", user.getAuthority().getAuthority());

        return "profile";
    }
}
