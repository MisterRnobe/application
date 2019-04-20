package ru.nikitamedvedev.application.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    private String name;
    private Integer maxScore;
    private LocalDateTime starts;
    private LocalDateTime finishes;
    private List<String> groups;
    private Long fileId;

}
