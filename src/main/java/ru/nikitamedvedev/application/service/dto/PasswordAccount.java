package ru.nikitamedvedev.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PasswordAccount {
    private String login;
    private String name;
    private String password;
}
