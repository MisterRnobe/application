package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResult {

    private Long id;
    private Long assignmentBindingId;
    private String created;
    private Long fileId;
    private String comment;
    private Integer scores;
    private Status status;
}
