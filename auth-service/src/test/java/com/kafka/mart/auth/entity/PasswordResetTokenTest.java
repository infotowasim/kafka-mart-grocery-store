package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PasswordResetTokenTest {

    private static final LocalDateTime TEST_TIME =
            LocalDateTime.of(
                    2025,
                    1,
                    1,
                    10,
                    0
            );

    @Test
    void builder_shouldCreatePasswordResetToken() {

        User user =
                new User();

        PasswordResetToken token =
                PasswordResetToken.builder()
                        .id(1L)
                        .token("reset-token")
                        .expiryDate(TEST_TIME)
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
                TEST_TIME,
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

        token.setId(2L);
        token.setToken("token-123");
        token.setExpiryDate(TEST_TIME);
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
                TEST_TIME,
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

        assertNotNull(
                token
        );
    }
}