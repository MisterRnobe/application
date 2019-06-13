package ru.nikitamedvedev.application.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.nikitamedvedev.application.config.SecurityConfig;
import ru.nikitamedvedev.application.service.dto.JwtAccount;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecurityConfig securityConfig;
    private final JwtCoderService jwtCoderService;

    public boolean hasAccess(String url, String token) {
        val accessToNonAuth = securityConfig.getNoAuthRules().stream()
                .anyMatch(strUrl -> url.toLowerCase().contains(strUrl));
        if (accessToNonAuth) {
            return true;
        }
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        JwtAccount user;
        try {
            user = jwtCoderService.decode(token);
        } catch (RuntimeException exception) {
            log.warn("Got error! ", exception);
            return false;
        }
        log.info("Got user: {}", user);

        if (user.getAvailableUntil().isBefore(OffsetDateTime.now())) {
            return false;
        }
        return securityConfig.getRoleAccessRule().get(user.getRole())
                .stream()
                .anyMatch(strUrl -> url.toLowerCase().contains(strUrl));
    }
}
