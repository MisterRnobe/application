package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BindAssignmentRequest {

    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private List<String> boundGroups;

}
