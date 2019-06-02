package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.service.dto.Group;
import ru.nikitamedvedev.application.service.dto.Subject;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupBindingResponse {

    private Group group;
    private List<Subject> subjects;

}
