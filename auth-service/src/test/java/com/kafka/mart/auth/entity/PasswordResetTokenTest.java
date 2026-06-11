package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PasswordResetTokenTest {

    @Test
    void builder_shouldCreatePasswordResetToken() {

        User user =
                new User();

        LocalDateTime expiryDate =
                LocalDateTime.now();

        PasswordResetToken token =
                PasswordResetToken.builder()
                        .id(1L)
                        .token("reset-token")
                        .expiryDate(expiryDate)
                        .user(user)
                        .build();

        assertEquals(
                1L,
                token.getId()
        );

        assertEquals(
                "reset-token",
                token.getToken()
        );

        assertEquals(
                expiryDate,
                token.getExpiryDate()
        );

        assertEquals(
                user,
                token.getUser()
        );
    }

    @Test
    void settersAndGetters_shouldWork() {

        PasswordResetToken token =
                new PasswordResetToken();

        User user =
                new User();

        LocalDateTime expiryDate =
                LocalDateTime.now();

        token.setId(2L);
        token.setToken("token-123");
        token.setExpiryDate(expiryDate);
        token.setUser(user);

        assertEquals(
                2L,
                token.getId()
        );

        assertEquals(
                "token-123",
                token.getToken()
        );

        assertEquals(
                expiryDate,
                token.getExpiryDate()
        );

        assertEquals(
                user,
                token.getUser()
        );
    }

    @Test
    void noArgsConstructor_shouldWork() {

        PasswordResetToken token =
                new PasswordResetToken();

        assertNotNull(token);
    }

    @Test
    void allArgsConstructor_shouldWork() {

        User user =
                new User();

        LocalDateTime expiryDate =
                LocalDateTime.now();

        PasswordResetToken token =
                new PasswordResetToken(
                        1L,
                        "reset-token",
                        expiryDate,
                        user
                );

        assertEquals(
                1L,
                token.getId()
        );

        assertEquals(
                "reset-token",
                token.getToken()
        );

        assertEquals(
                expiryDate,
                token.getExpiryDate()
        );

        assertEquals(
                user,
                token.getUser()
        );
    }
}