package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nikitamedvedev.application.service.UserService;
import ru.nikitamedvedev.application.web.dto.CreateTeacherRequest;

@RestController
@Slf4j
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping(value = "/create-teacher")
    public void createTeacher(CreateTeacherRequest createTeacherRequest) {
        log.info("Creating teacher with login: {}", createTeacherRequest);
        userService.createTeacher(createTeacherRequest.getLogin(), createTeacherRequest.getName());
    }


//    @PostMapping(path = "/add")
//    public ResponseEntity<Resource> addUsers(@RequestParam("userList") MultipartFile file,
//                                             @RequestParam("name") String name) throws IOException {
//        // TODO: 09.02.2019 Move to UserService class
//        List<Object> parsed = objectMapper.readValue(file.getBytes(), ArrayList.class);
//        List<String> names = parsed.stream()
//                .map(Object::toString)
//                .collect(Collectors.toList());
//        List<Account> users = userService.createGroupWithUsers(name, names);
//
//        ByteArrayOutputStream stub = new ByteArrayOutputStream();
//        objectMapper.writeValue(stub, users);
//        Resource resource = new ByteArrayResource(stub.toByteArray());
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + name + ".json\"").body(resource);
//    }
}

