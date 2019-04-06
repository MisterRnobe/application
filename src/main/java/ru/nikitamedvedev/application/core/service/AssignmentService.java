package ru.nikitamedvedev.application.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nikitamedvedev.application.core.client.db.AssignmentRepository;
import ru.nikitamedvedev.application.core.client.db.GroupRepository;
import ru.nikitamedvedev.application.core.client.db.ResultRepository;
import ru.nikitamedvedev.application.core.client.db.UserRepository;
import ru.nikitamedvedev.application.core.client.db.dto.*;
import ru.nikitamedvedev.application.core.service.dto.AssignmentResult;
import ru.nikitamedvedev.application.core.service.dto.UnprocessedWork;
import ru.nikitamedvedev.application.core.user.User;
import ru.nikitamedvedev.application.web.dto.CreateAssignmentRequest;
import ru.nikitamedvedev.application.web.model.FileResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ResultRepository resultRepository;

    public void storeAssignment(CreateAssignmentRequest request) {
        AssignmentDb assignmentDb = AssignmentDb.builder()
                .name(request.getName())
                .maxScores(request.getMaxScore())
                .starts(request.getStarts())
                .finishes(request.getFinishes())
                .source(request.getSource())
                .fileName(request.getFileName())
                .groups(groupRepository.findByNameIn(request.getGroups()))
                .build();
        assignmentRepository.save(assignmentDb);
    }

    public Map<Long, String> getGroups() {
        return StreamSupport.stream(groupRepository.findAll().spliterator(), false)
                .collect(Collectors.toMap(GroupDb::getId, GroupDb::getName));
    }

    public List<AssignmentResult> getAssignmentResults(String login) {
        UserDb user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found!"));
        List<AssignmentDb> assignments = user.getGroupDb().getAssignments();
        return assignments.stream()
                .map(db -> AssignmentResult.builder()
                        .finishes(db.getFinishes())
                        .starts(db.getStarts())
                        .maxScores(db.getMaxScores())
                        .name(db.getName())
                        .assignmentId(db.getId())
                        .build())
                .peek(assignmentResult ->
                        {
                            ResultDb resultDb = resultRepository.findByAssignmentId(assignmentResult.getAssignmentId())
                                    .orElse(ResultDb.builder()
                                            .status(ResultStatus.NOT_UPLOADED)
                                            .result(0)
                                            .build());
                            assignmentResult.setEarnedScores(resultDb.getResult());
                            assignmentResult.setResultStatus(resultDb.getStatus());
                        }
                )
                .collect(Collectors.toList());
    }

    public void saveNewResult(Long id, String login, MultipartFile file) throws IOException {
        ResultDb resultDb = ResultDb.builder()
                .status(ResultStatus.NEW)
                .user(userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found!")))
                .assignment(assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found!")))
                .source(file.getBytes())
                .fileName(file.getOriginalFilename())
                .build();
        resultRepository.save(resultDb);
    }

    public FileResource getAssignmentSource(Long id) {
        AssignmentDb assignmentDb = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found!"));
        return new FileResource(new ByteArrayResource(assignmentDb.getSource()), assignmentDb.getFileName());
    }

    public List<UnprocessedWork> getNewWorks(Long groupId, Long userId) {
        List<UserDb> users;
        if (userId == null) {
            users = userRepository.findByGroupDb_Id(groupId);
        } else {
            users = Arrays.asList(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!")));
        }
        // TODO: 09.02.2019 add time to this POJO
        return users.stream()
                .flatMap(userDb -> resultRepository.findByUser_IdAndStatus(userDb.getId(), ResultStatus.NEW).stream()
                        .map(resultDb ->
                                UnprocessedWork.builder()
                                        .resultId(resultDb.getId())
                                        .groupName(userDb.getGroupDb().getName())
                                        .status(resultDb.getStatus())
                                        .userId(userDb.getId())
                                        .userName(userDb.getName())
                                        .assignmentId(resultDb.getAssignment().getId())
                                        .build()))
                .collect(Collectors.toList());


    }

    public List<User> getUsersInGroup(Long groupId) {
        return userRepository.findByGroupDb_Id(groupId).stream()
                .map(userDb -> User.builder()
                        .login(userDb.getLogin())
                        .fullName(userDb.getName())
                        .groupName(userDb.getGroupDb().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public FileResource getResultResource(Long id) {
        ResultDb resultDb = resultRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment has not found!"));
        Resource resource = new ByteArrayResource(resultDb.getSource());
        return new FileResource(resource, resultDb.getFileName());
    }
}
