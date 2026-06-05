package com.kafka.mart.auth.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderConfigTest {

    @Test
    void passwordEncoderSuccess() {

        PasswordEncoderConfig config =
                new PasswordEncoderConfig();

        PasswordEncoder encoder =
                config.passwordEncoder();

        assertNotNull(encoder);

        String encoded =
                encoder.encode("password");

        assertTrue(
                encoder.matches(
                        "password",
                        encoded
                )
        );
    }
}