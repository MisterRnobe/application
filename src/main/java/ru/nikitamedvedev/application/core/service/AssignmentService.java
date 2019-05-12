package ru.nikitamedvedev.application.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitamedvedev.application.core.client.db.AssignmentRepository;
import ru.nikitamedvedev.application.core.client.db.UserRepository;
import ru.nikitamedvedev.application.core.client.db.dto.AssignmentDb;
import ru.nikitamedvedev.application.core.client.db.dto.FileDb;
import ru.nikitamedvedev.application.core.service.dto.Assignment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final Converter<AssignmentDb, Assignment> converter;
//    private final FileRepository fileRepository;

    @Transactional
    public void createAssignment(String name, String fileName, byte[] file, String postedBy) {
        val userDb = userRepository.findById(postedBy).orElseThrow(() -> new RuntimeException(String.format("User %s has not found!", postedBy)));

        AssignmentDb assignmentDb = AssignmentDb.builder()
                .name(name)
                .file(new FileDb(null, fileName, file))
                .posted(userDb)
                .build();
        assignmentRepository.save(assignmentDb);
    }

    public List<Assignment> getAssignmentsByCreator(String login) {
        return assignmentRepository.findByPosted_Login(login).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public void deleteAssignment(Long assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }

    public void updateAssignment(Long id, String name, String fileName, byte[] fileContent) {
        val assignmentDb = assignmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Assignment " + id + " was not found!"));
        val boundFile = assignmentDb.getFile();

        if (fileName != null && fileContent != null) {
            boundFile.setFile(fileContent);
            boundFile.setFileName(fileName);
        }

        if (name != null) {
            assignmentDb.setName(name);
        }

        assignmentRepository.save(assignmentDb);
    }
}
