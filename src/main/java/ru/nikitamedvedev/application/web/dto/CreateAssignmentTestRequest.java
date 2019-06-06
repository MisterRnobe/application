package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.service.dto.Question;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssignmentTestRequest {

    private String name;
    private List<Question> questions;
    private Integer scores;

}
