package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentTest {

    private Long assignmentId;
    private String name;
    private List<Question> questions;
    private TeacherUser createdBy;

}
