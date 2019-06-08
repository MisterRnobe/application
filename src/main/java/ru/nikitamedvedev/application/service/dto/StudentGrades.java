package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentGrades {

    private String subject;
    private String teacherName;

    private Map<String, Grade> assignmentNameToGrade;
}
