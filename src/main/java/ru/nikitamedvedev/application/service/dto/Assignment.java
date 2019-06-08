package ru.nikitamedvedev.application.service.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    private Long id;
    private String name;
    private TeacherUser createdBy;
    private Long fileId;

}
