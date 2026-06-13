package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenTest {

    private static final LocalDateTime TEST_TIME =
            LocalDateTime.of(
                    2025,
                    1,
                    1,
                    10,
                    0
            );

    @Test
    void builder_shouldCreateRefreshToken() {

        User user =
                new User();

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .id(1L)
                        .token("refresh-token")
                        .expiryDate(TEST_TIME)
                        .user(user)
                        .build();

        assertEquals(
                1L,
                refreshToken.getId()
        );

        assertEquals(
                "refresh-token",
                refreshToken.getToken()
        );

        assertEquals(
                TEST_TIME,
                refreshToken.getExpiryDate()
        );

        assertEquals(
                user,
                refreshToken.getUser()
        );
    }

    @Test
    void settersAndGetters_shouldWork() {

        RefreshToken refreshToken =
                new RefreshToken();

        User user =
                new User();

        refreshToken.setId(2L);
        refreshToken.setToken("token-123");
        refreshToken.setExpiryDate(TEST_TIME);
        refreshToken.setUser(user);

        assertEquals(
                2L,
                refreshToken.getId()
        );

        assertEquals(
                "token-123",
                refreshToken.getToken()
        );

        assertEquals(
                TEST_TIME,
                refreshToken.getExpiryDate()
        );

        assertEquals(
                user,
                refreshToken.getUser()
        );
    }

    @Test
    void noArgsConstructor_shouldWork() {

        RefreshToken refreshToken =
                new RefreshToken();

        assertNotNull(
                refreshToken
        );
    }
}