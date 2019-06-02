package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitamedvedev.application.persistence.dto.ResultStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnprocessedWork {

    private Long resultId;
    private Long userId;
    private Long assignmentId;

    private String userName;
    private String groupName;
    private ResultStatus status;

}

