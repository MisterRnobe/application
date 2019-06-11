package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.service.dto.Grade;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAssignmentGradeResponse {

    private String studentName;
    private Map<Long, Grade> bindingIdToGrade;

}
