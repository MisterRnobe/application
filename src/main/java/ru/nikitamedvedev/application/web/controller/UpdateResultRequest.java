package ru.nikitamedvedev.application.web.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.service.dto.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResultRequest {

    private String comment;
    private Status status;
    private Integer scores;

}
