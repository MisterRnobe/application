package ru.nikitamedvedev.application.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping
@Controller
@Slf4j
public class LoginController {

    @GetMapping(path = "/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        return "login";
    }
}
