package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.service.dto.Assignment;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupGrades {

    private String groupName;
    private List<Assignment> assignments;
    private List<StudentAssignmentGradeResponse> grades;
}
