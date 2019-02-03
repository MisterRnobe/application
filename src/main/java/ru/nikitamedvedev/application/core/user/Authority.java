package ru.nikitamedvedev.application.core.user;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
