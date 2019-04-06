package ru.nikitamedvedev.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.nikitamedvedev.application.core.hepler.PasswordGenerator;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/profile");
    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new MvcConfig.DummyEncoder();
    }

    @Slf4j
    private static class DummyEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence charSequence) {
            log.info("Received: {}", charSequence);
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            log.info("Pass: {}, encrypted: {}", charSequence.toString(), s);
            return charSequence.toString().equals(s);
        }
    }
}
