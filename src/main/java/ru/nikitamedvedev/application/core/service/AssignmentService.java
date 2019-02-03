package ru.nikitamedvedev.application.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nikitamedvedev.application.core.client.db.AssignmentRepository;
import ru.nikitamedvedev.application.core.client.db.GroupRepository;
import ru.nikitamedvedev.application.core.client.db.dto.AssignmentDb;
import ru.nikitamedvedev.application.web.dto.CreateAssignmentRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final GroupRepository groupRepository;

    public void storeAssignment(CreateAssignmentRequest request, byte[] fileBytes) {
        AssignmentDb assignmentDb = AssignmentDb.builder()
                .name(request.getName())
                .maxScores(request.getMaxScore())
                .starts(request.getStarts())
                .finishes(request.getFinishes())
                .source(fileBytes)
                .build();
        assignmentRepository.save(assignmentDb);
    }
}
