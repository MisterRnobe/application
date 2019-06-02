package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    private Long id;
    private String name;
    private Long fileId;
    private TeacherUser createdBy;

}
