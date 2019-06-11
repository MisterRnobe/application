package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import ru.nikitamedvedev.application.service.GroupService;
import ru.nikitamedvedev.application.service.UserService;
import ru.nikitamedvedev.application.service.dto.Group;
import ru.nikitamedvedev.application.service.dto.StudentUser;
import ru.nikitamedvedev.application.service.dto.Subject;
import ru.nikitamedvedev.application.web.dto.GroupBindingResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @GetMapping(path = "/get-all")
    public List<Group> getAllGroups() {
        log.info("Get all groups request");
        return groupService.getAllGroups();
    }

    @GetMapping(path = "/get-by-teacher/{login}")
    public List<Group> getByTeacher(@PathVariable String login) {
        return groupService.getByTeacher(login);
    }

    @GetMapping(path = "/get-students-by-group/{groupId}")
    public List<StudentUser> getByGroup(@PathVariable Long groupId) {
        return userService.getStudentsByGroup(groupId);
    }

}
