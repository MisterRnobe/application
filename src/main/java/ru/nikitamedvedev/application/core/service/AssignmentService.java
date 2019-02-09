package ru.nikitamedvedev.application.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nikitamedvedev.application.core.client.db.AssignmentRepository;
import ru.nikitamedvedev.application.core.client.db.GroupRepository;
import ru.nikitamedvedev.application.core.client.db.ResultRepository;
import ru.nikitamedvedev.application.core.client.db.UserRepository;
import ru.nikitamedvedev.application.core.client.db.dto.AssignmentDb;
import ru.nikitamedvedev.application.core.client.db.dto.GroupDb;
import ru.nikitamedvedev.application.core.client.db.dto.UserDb;
import ru.nikitamedvedev.application.core.service.dto.AssignmentResult;
import ru.nikitamedvedev.application.web.dto.CreateAssignmentRequest;

import java.util.Collections;
import java.util.List;
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

    public void storeAssignment(CreateAssignmentRequest request, byte[] fileBytes) {
        AssignmentDb assignmentDb = AssignmentDb.builder()
                .name(request.getName())
                .maxScores(request.getMaxScore())
                .starts(request.getStarts())
                .finishes(request.getFinishes())
                .source(fileBytes)
                .groups(groupRepository.findByNameIn(request.getGroups()))
                .build();
        assignmentRepository.save(assignmentDb);
    }

    public List<String> getGroups() {
        return StreamSupport.stream(groupRepository.findAll().spliterator(), false)
                .map(GroupDb::getName)
                .collect(Collectors.toList());
    }

    public List<AssignmentResult> getAssignmentResults(String login) {
        UserDb user = userRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("User not found!"));
        List<AssignmentDb> assignments = user.getGroupDb().getAssignments();
        return assignments.stream()
                .map(db -> AssignmentResult.builder()
                        .earnedScores(0)
                        .finishes(db.getFinishes())
                        .starts(db.getStarts())
                        .isCompleted(false)
                        .maxScores(db.getMaxScores())
                        .name(db.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
