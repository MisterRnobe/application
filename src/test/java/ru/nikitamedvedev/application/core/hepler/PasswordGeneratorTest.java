package ru.nikitamedvedev.application.core.hepler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordGeneratorTest {

    private PasswordGenerator passwordGenerator;

    @Before
    public void setUp() throws Exception {
        passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
    }

    @Test
    public void shouldGeneratePassword() {
        String password = passwordGenerator.generate(10);
        System.out.printf("Generated: %s\n\r", password);
    }
}