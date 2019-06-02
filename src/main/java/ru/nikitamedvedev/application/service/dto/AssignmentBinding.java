package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentBinding {

    private Assignment assignment;
    private TeacherUser created;
    private Semester semester;
    private Group group;
    private Subject subject;
    private Integer scores;
    private LocalDate starts;

}
