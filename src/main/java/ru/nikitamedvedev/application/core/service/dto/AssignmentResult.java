package ru.nikitamedvedev.application.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AssignmentResult {

    private LocalDateTime starts;
    private LocalDateTime finishes;
    private Integer maxScores;
    private String name;
    private Boolean isCompleted;
    private Integer earnedScores;

}
