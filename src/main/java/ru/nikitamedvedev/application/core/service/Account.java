package ru.nikitamedvedev.application.core.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Account {
    private String login;
    private String name;
    private String password;
}
