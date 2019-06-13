package ru.nikitamedvedev.application.service;

import com.google.common.collect.ImmutableMap;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import ru.nikitamedvedev.application.config.SecurityConfig;
import ru.nikitamedvedev.application.service.dto.JwtAccount;
import ru.nikitamedvedev.application.service.dto.Role;
import ru.nikitamedvedev.application.service.security.JwtCoderService;
import ru.nikitamedvedev.application.service.security.JwtService;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class JwtServiceTest {

    private JwtService jwtService;
    private JwtCoderService jwtCoderService;

    private static final String TOKEN = "token";

    @Before
    public void init() {
        val securityConfig = new SecurityConfig(ImmutableMap.of(
                Role.STUDENT, Collections.singletonList("/url1/"),
                Role.TEACHER, Collections.singletonList("/url2/"),
                Role.ADMIN, Collections.singletonList("/url3/")
        ), Collections.singletonList("/url4/"));
        jwtCoderService = mock(JwtCoderService.class);
        jwtService = new JwtService(securityConfig, jwtCoderService);
    }

    @Test
    public void shouldHaveAccessIfTokenIsOk() {
        when(jwtCoderService.decode(TOKEN))
                .thenReturn(new JwtAccount("login", null, null, Role.STUDENT, OffsetDateTime.now().plusDays(1)));

        val hasAccess = jwtService.hasAccess("/url1/login", TOKEN);

        assertTrue(hasAccess);
    }

    @Test
    public void shouldHaveAccessToNonAuthUrlWithToken() {
        when(jwtCoderService.decode(TOKEN))
                .thenReturn(new JwtAccount("login", null, null, Role.STUDENT, OffsetDateTime.now().plusDays(1)));

        val hasAccess = jwtService.hasAccess("/url4/login", TOKEN);

        assertTrue(hasAccess);
    }

    @Test
    public void shouldHaveAccessToNonAuthUrlWithoutToken() {
        val hasAccess = jwtService.hasAccess("/url4/somedata", null);

        assertTrue(hasAccess);
    }

    @Test
    public void shouldNotHaveAccessIfTokenIsExpired() {
        when(jwtCoderService.decode(TOKEN))
                .thenReturn(new JwtAccount("login", null, null, Role.STUDENT, OffsetDateTime.now().minusDays(1)));

        val hasAccess = jwtService.hasAccess("/url1/login", TOKEN);

        assertFalse(hasAccess);
    }

    @Test
    public void shouldNotHaveAccessIfForbidden() {
        when(jwtCoderService.decode(TOKEN))
                .thenReturn(new JwtAccount("login", null, null, Role.STUDENT, OffsetDateTime.now().minusDays(1)));

        val hasAccess = jwtService.hasAccess("/url2/login", TOKEN);

        assertFalse(hasAccess);
    }

    @Test
    public void shouldNotHaveAccessIfHasNoToken() {
        val hasAccess = jwtService.hasAccess("/url2/login", null);

        assertFalse(hasAccess);
    }

    @Test
    public void shouldNotHaveAccessIfTokenIsBad() {
        when(jwtCoderService.decode(TOKEN))
                .thenThrow(new RuntimeException());

        val hasAccess = jwtService.hasAccess("/url2/login", TOKEN);

        assertFalse(hasAccess);
    }
}