package ru.nikitamedvedev.application.service;

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
