package ru.nikitamedvedev.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPasswordRequest {

    private String login;
    private String password;
}
