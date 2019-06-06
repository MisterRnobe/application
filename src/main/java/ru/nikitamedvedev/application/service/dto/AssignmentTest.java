package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentTest {

    private Long id;
    private String name;
    private List<Question> questions;
    private Integer scores;
    private TeacherUser createdBy;

}
