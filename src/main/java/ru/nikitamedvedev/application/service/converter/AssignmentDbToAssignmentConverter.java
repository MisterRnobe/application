package ru.nikitamedvedev.application.service.converter;

import lombok.RequiredArgsConstructor;
import lombok.val;
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
        val assignment = new Assignment();
        assignment.setId(assignmentDb.getId());
        assignment.setName(assignmentDb.getName());
        assignment.setCreatedBy(userConverter.convert(assignmentDb.getPosted()));
        assignment.setFileId(assignmentDb.getFile().getId());
        return assignment;
    }
}
