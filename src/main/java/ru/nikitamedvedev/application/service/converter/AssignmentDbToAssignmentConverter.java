package ru.nikitamedvedev.application.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.AssignmentDb;
import ru.nikitamedvedev.application.persistence.dto.TeacherUserDb;
import ru.nikitamedvedev.application.service.dto.Assignment;
import ru.nikitamedvedev.application.service.dto.TeacherUser;

@Component
@RequiredArgsConstructor
public class AssignmentDbToAssignmentConverter implements Converter<AssignmentDb, Assignment> {

    private final Converter<TeacherUserDb, TeacherUser> userConverter;

    @Override
    public Assignment convert(AssignmentDb assignmentDb) {
        return Assignment.builder()
                .id(assignmentDb.getId())
                .name(assignmentDb.getName())
                .createdBy(userConverter.convert(assignmentDb.getPosted()))
                .fileId(assignmentDb.getFile().getId())
                .build();
    }
}
