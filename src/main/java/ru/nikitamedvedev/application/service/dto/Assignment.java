package ru.nikitamedvedev.application.service.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Assignment extends CommonAssignment {

    {
        this.setType(AssignmentType.ASSIGNMENT);
    }

    private Long fileId;

}
