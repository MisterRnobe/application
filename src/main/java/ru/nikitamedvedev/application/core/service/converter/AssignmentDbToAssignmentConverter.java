package ru.nikitamedvedev.application.core.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.core.client.db.dto.AssignmentDb;
import ru.nikitamedvedev.application.core.client.db.dto.UserDb;
import ru.nikitamedvedev.application.core.service.dto.Assignment;
import ru.nikitamedvedev.application.core.service.dto.User;

@Component
@RequiredArgsConstructor
public class AssignmentDbToAssignmentConverter implements Converter<AssignmentDb, Assignment> {

    private final Converter<UserDb, User> userConverter;

    @Override
    public Assignment convert(AssignmentDb assignmentDb) {
        return Assignment.builder()
                .assignmentId(assignmentDb.getId())
                .name(assignmentDb.getName())
                .createdBy(userConverter.convert(assignmentDb.getPosted()))
                .fileId(assignmentDb.getFile().getId())
                .build();
    }
}
