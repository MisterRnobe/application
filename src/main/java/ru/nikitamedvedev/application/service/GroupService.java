package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.AssignmentBindingRepository;
import ru.nikitamedvedev.application.persistence.GroupRepository;
import ru.nikitamedvedev.application.persistence.SubjectRepository;
import ru.nikitamedvedev.application.persistence.dto.AssignmentBindingDb;
import ru.nikitamedvedev.application.persistence.dto.GroupDb;
import ru.nikitamedvedev.application.persistence.dto.SubjectDb;
import ru.nikitamedvedev.application.service.converter.GroupDbToGroupConverter;
import ru.nikitamedvedev.application.service.dto.Group;
import ru.nikitamedvedev.application.service.dto.Subject;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupDbToGroupConverter converter;

    private final AssignmentBindingRepository assignmentBindingRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAllBy().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public List<Group> getByTeacher(String login) {
        return assignmentBindingRepository.findByCreated_Login(login).stream()
                .map(AssignmentBindingDb::getGroup)
                .map(converter::convert)
                .distinct()
                .collect(Collectors.toList());
    }
}
