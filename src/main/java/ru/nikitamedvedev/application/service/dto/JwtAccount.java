package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAccount {

    private String login;
    private String groupName;
    private Long groupId;
    private Role role;
    private OffsetDateTime availableUntil;

}
