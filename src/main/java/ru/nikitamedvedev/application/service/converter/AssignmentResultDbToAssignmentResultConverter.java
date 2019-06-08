package ru.nikitamedvedev.application.service.converter;

import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.nikitamedvedev.application.persistence.dto.AssignmentResultDb;
import ru.nikitamedvedev.application.service.dto.AssignmentResult;

@Component
public class AssignmentResultDbToAssignmentResultConverter implements Converter<AssignmentResultDb, AssignmentResult> {
    @Override
    public AssignmentResult convert(AssignmentResultDb assignmentResultDb) {
        val assignmentResult = new AssignmentResult();
        assignmentResult.setId(assignmentResultDb.getId());
        assignmentResult.setCreated(assignmentResultDb.getCreated().getLogin());
        assignmentResult.setFileId(assignmentResultDb.getFile().getId());
        assignmentResult.setScores(assignmentResultDb.getScores());
        assignmentResult.setStatus(assignmentResultDb.getStatus());
        assignmentResult.setAssignmentBindingId(assignmentResultDb.getAssignmentBinding().getId());
        assignmentResult.setComment(assignmentResultDb.getComment());
        return assignmentResult;
    }
}
