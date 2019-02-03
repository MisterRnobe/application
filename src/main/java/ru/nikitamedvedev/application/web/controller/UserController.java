package ru.nikitamedvedev.application.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.core.service.Account;
import ru.nikitamedvedev.application.core.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final ObjectMapper objectMapper;
    private final UserService userService;

    @GetMapping
    public String controlPanel() {
        return "user/main";
    }

    @GetMapping("/edit")
    public String editUsersPage() {
        return "user/edit";
    }

    @GetMapping("/add")
    public String addUsersPage() {
        return "user/add";
    }

    @PostMapping(path = "/add")
    public void addUsers(@RequestParam("userList") MultipartFile file,
                         @RequestParam("groupName") String groupName,
                         HttpServletResponse response) throws IOException {
        List<Object> parsed = objectMapper.readValue(file.getBytes(), ArrayList.class);
        List<String> names = parsed.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        List<Account> users = userService.createGroupWithUsers(groupName, names);

        objectMapper.writeValue(response.getOutputStream(), users);
        response.flushBuffer();
    }
}

