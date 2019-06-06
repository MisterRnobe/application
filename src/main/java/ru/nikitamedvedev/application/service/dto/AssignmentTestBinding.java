package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentTestBinding {

    private Long id;
    private AssignmentTest assignmentTest;
    private TeacherUser created;
    private Semester semester;
    private Group group;
    private Subject subject;
    private Integer scores;
    private LocalDate starts;
    private Integer duration;

}
