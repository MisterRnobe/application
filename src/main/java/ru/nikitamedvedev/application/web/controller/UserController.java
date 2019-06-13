package ru.nikitamedvedev.application.web.controller;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import ru.nikitamedvedev.application.service.UserService;
import ru.nikitamedvedev.application.service.security.JwtCoderService;
import ru.nikitamedvedev.application.web.dto.CreateTeacherRequest;
import ru.nikitamedvedev.application.web.dto.LoginPasswordRequest;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtCoderService jwtCoderService;


    @PutMapping(value = "/create-teacher")
    public void createTeacher(CreateTeacherRequest createTeacherRequest) {
        log.info("Creating teacher with login: {}", createTeacherRequest);
        userService.createTeacher(createTeacherRequest.getLogin(), createTeacherRequest.getName());
    }

    @PostMapping(path = "/authorize")
    public Map<String, Object> authorize(@RequestBody LoginPasswordRequest request) {
        val jwtAccount = userService.authorize(request.getLogin(), request.getPassword());
        if (jwtAccount == null) {
            throw new IllegalStateException("Bad data!");
        }
        return ImmutableMap.of(
                "token", jwtCoderService.encode(jwtAccount),
                "data", jwtAccount
        );
    }

}

