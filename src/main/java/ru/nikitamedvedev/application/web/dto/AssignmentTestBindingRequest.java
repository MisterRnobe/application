package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentTestBindingRequest {

    @NotNull
    private Long groupId;
    @NotNull
    private Long assignmentTestId;
    @NotNull
    private Long semesterId;
    @NotNull
    private Long subjectId;
    @NotNull
    private Integer scores;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private Integer duration;

}
