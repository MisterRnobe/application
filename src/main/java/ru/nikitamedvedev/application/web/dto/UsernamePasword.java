package ru.nikitamedvedev.application.web.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class UsernamePasword {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
