package ru.nikitamedvedev.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.GroupRepository;
import ru.nikitamedvedev.application.persistence.SubjectRepository;
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
    private final SubjectRepository subjectRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAllBy().stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public void createGroup(String name) {
        groupRepository.save(new GroupDb(null, name, Collections.emptyList()));
    }

    public void bindToSubject(Long groupId, Long subjectId) {
        GroupDb group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " was not found"));
        group.getSubjectDbs().add(subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException("Subject with id " + subjectId + " was not found")));
        GroupDb save = groupRepository.save(group);
        log.info("saved: {}", save);
    }

    public Stream<Pair<Group, List<Subject>>> getAllBindingsToGroup() {
        return groupRepository.findAllBy().stream()
                .filter(groupDb -> !groupDb.getSubjectDbs().isEmpty())
                .map(groupDb -> Pair.of(converter.convert(groupDb), convertSubjects(groupDb.getSubjectDbs())));
    }

    private List<Subject> convertSubjects(List<SubjectDb> subjectDbs) {
        return subjectDbs.stream()
                .map(subjectDb -> new Subject(subjectDb.getId(), subjectDb.getSubjectName()))
                .collect(Collectors.toList());
    }
}
