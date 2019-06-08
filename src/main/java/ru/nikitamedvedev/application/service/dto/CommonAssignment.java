package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonAssignment {

    private Long id;
    private String name;
    private TeacherUser createdBy;
    private AssignmentType type;

}
