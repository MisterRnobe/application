package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssignmentRequest {

    private String name;
    private Integer maxScore;
    private LocalDateTime starts;
    private LocalDateTime finishes;
    private List<String> groups;
}
