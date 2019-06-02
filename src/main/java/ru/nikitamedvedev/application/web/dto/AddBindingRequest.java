package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBindingRequest {

    private Long groupId;
    private Long assignmentId;
    private Long semesterId;
    private Long subjectId;
    private Integer scores;
    private LocalDate startDate;
}
