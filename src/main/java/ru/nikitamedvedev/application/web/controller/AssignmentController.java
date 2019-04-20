package ru.nikitamedvedev.application.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.core.service.AssignmentService;
import ru.nikitamedvedev.application.core.service.dto.Assignment;
import ru.nikitamedvedev.application.web.dto.BindAssignmentRequest;
import ru.nikitamedvedev.application.web.dto.BoundAssignmentResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/assignment")
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('ADMIN')")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @SneakyThrows
    @PutMapping(path = "/add")
    public void addAssignment(@RequestParam("file") MultipartFile file,
                              @RequestParam("name") String name) {
        log.info("Received add assignment response: {}", name);
        file.getBytes();
        assignmentService.createAssignment(name, file.getName(), file.getBytes());
        log.info("Assignment stored processed");
    }

    @PostMapping(path = "/get-by-ids")
    public List<Assignment> getAssignmentsByIds(@RequestBody List<Long> ids) {
        log.info("Received get assignment by id request: {}", ids);
        List<Assignment> assignments = assignmentService.getAssignmentByIds(ids);
        log.info("Found assignment. Returning...");
        return assignments;
    }

    @PostMapping("/bind/{id}")
    public void bindAssignment(@PathVariable Long id, @RequestBody BindAssignmentRequest request) {
        log.info("Received bound request");
        assignmentService.bindAssignment(id, request.getBoundGroups(), request.getStartTime(), request.getEndTime());
        log.info("Bound request has been processed");
    }

    @PostMapping(path = "/get-all-by-groups")
    public List<BoundAssignmentResponse> getAllByGroups(@RequestBody List<String> groupNames) {
        log.info("Received get all by groups response: {}", groupNames);
        assignmentService
    }

//
//    @GetMapping(path = "/new_works")
//    public String getNewWorks(Model model) {
//        model.addAttribute("groups", assignmentService.getGroups());
//        return "assignment/new_work";
//    }
//
//    @GetMapping(path = "/result/{id}")
//    public ResponseEntity<Resource> getResult(@PathVariable Long id) {
//        FileResource resultResource = assignmentService.getResultResource(id);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + resultResource.getFileName() + "\"")
//                .body(resultResource.getResource());
//
//
//    }
//
//    @PostMapping(path = "/new_works")
//    public String getNewWorks(@RequestParam Long groupId,
//                              @RequestParam(required = false) Long userId,
//                              Model model) {
//
//        List<UnprocessedWork> newWorks = assignmentService.getNewWorks(groupId, userId);
//        Map<Long, String> availableGroups = assignmentService.getGroups();
//        List<User> availableUsers = assignmentService.getUsersInGroup(groupId);
//
//        model.addAttribute("selectedGroup", availableGroups.get(groupId));
//
//        model.addAttribute("groups", availableGroups);
//        model.addAttribute("users", availableUsers);
//        model.addAttribute("newWorks", newWorks);
//
//        return "assignment/new_work";
//    }

}
