package com.kafka.mart.auth.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderConfigTest {

    private final PasswordEncoderConfig config =
            new PasswordEncoderConfig();

    @Test
    void passwordEncoder_shouldReturnBCryptPasswordEncoder() {

        PasswordEncoder encoder =
                config.passwordEncoder();

        assertNotNull(encoder);

        assertInstanceOf(
                BCryptPasswordEncoder.class,
                encoder
        );
    }

    @Test
    void passwordEncoder_shouldEncodeAndMatchPassword() {

        PasswordEncoder encoder =
                config.passwordEncoder();

        String rawPassword =
                "password123";

        String encodedPassword =
                encoder.encode(rawPassword);

        assertNotNull(encodedPassword);

        assertNotEquals(
                rawPassword,
                encodedPassword
        );

        assertTrue(
                encoder.matches(
                        rawPassword,
                        encodedPassword
                )
        );
    }
}