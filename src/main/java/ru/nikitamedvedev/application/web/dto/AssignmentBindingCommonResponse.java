package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.service.dto.AssignmentResult;
import ru.nikitamedvedev.application.service.dto.AssignmentType;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentBindingCommonResponse {

    private Long id;
    private String name;
    private String created;
    private Long fileId;
    private Long duration;
    private String semester;
    private AssignmentType type;
    private Integer scores;
    private LocalDate starts;

    private List<AssignmentResult> results;

}
