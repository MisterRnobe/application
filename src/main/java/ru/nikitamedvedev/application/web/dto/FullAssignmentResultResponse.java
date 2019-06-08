package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.service.dto.AssignmentBinding;
import ru.nikitamedvedev.application.service.dto.AssignmentResult;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullAssignmentResultResponse {

    private String studentName;
    private AssignmentBinding assignmentBinding;

    private AssignmentResult newResult;
    private List<AssignmentResult> oldResults;

}
