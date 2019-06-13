package ru.nikitamedvedev.application.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nikitamedvedev.application.service.dto.Role;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class SecurityConfig {

    private final Map<Role, List<String>> roleAccessRule;
    private final List<String> noAuthRules;

}
