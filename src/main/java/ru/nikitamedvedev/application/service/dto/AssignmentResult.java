package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.nikitamedvedev.application.persistence.dto.ResultStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AssignmentResult {

    private Long assignmentId;
    private LocalDateTime starts;
    private LocalDateTime finishes;
    private Integer maxScores;
    private String name;
    private Integer earnedScores;
    private ResultStatus resultStatus;
}
