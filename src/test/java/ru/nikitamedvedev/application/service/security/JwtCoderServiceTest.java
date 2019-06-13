package ru.nikitamedvedev.application.service.security;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import ru.nikitamedvedev.application.service.dto.JwtAccount;
import ru.nikitamedvedev.application.service.dto.Role;

import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;

public class JwtCoderServiceTest {

    private JwtCoderService jwtCoderService;

    @Before
    public void init() {
        jwtCoderService = new JwtCoderService(Algorithm.HMAC256("easypass"));
    }

    @Test
    public void shouldDecodeAndEncode() {
        val jwtAccount = new JwtAccount(
                "login",
                "group",
                1L,
                Role.STUDENT,
                OffsetDateTime.now()
        );

        val encode = jwtCoderService.encode(jwtAccount);
        val decode = jwtCoderService.decode(encode);

        assertEquals(jwtAccount, decode);
    }

    @Test
    public void shouldDecodeAndEncodeWithNullValues() {
        val jwtAccount = new JwtAccount(
                "login",
                null,
                null,
                Role.STUDENT,
                OffsetDateTime.now()
        );

        val encode = jwtCoderService.encode(jwtAccount);
        val decode = jwtCoderService.decode(encode);

        assertEquals(jwtAccount, decode);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfLoginIsNotPresent() {
        val jwtAccount = new JwtAccount(
                null,
                null,
                null,
                Role.STUDENT,
                OffsetDateTime.now()
        );

        val encode = jwtCoderService.encode(jwtAccount);
        val decode = jwtCoderService.decode(encode);
    }
}