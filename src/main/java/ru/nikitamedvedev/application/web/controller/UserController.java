package ru.nikitamedvedev.application.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    public ResponseEntity<Resource> addUsers(@RequestParam("userList") MultipartFile file,
                                             @RequestParam("groupName") String groupName) throws IOException {
        // TODO: 09.02.2019 Move to UserService class
        List<Object> parsed = objectMapper.readValue(file.getBytes(), ArrayList.class);
        List<String> names = parsed.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        List<Account> users = userService.createGroupWithUsers(groupName, names);

        ByteArrayOutputStream stub = new ByteArrayOutputStream();
        objectMapper.writeValue(stub, users);
        Resource resource = new ByteArrayResource(stub.toByteArray());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + groupName + ".json\"").body(resource);
    }
}

