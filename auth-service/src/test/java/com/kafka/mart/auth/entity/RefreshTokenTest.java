package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenTest {

    @Test
    void builder_shouldCreateRefreshToken() {

        User user =
                new User();

        LocalDateTime expiryDate =
                LocalDateTime.now();

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .id(1L)
                        .token("refresh-token")
                        .expiryDate(expiryDate)
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
                expiryDate,
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

        LocalDateTime expiryDate =
                LocalDateTime.now();

        refreshToken.setId(2L);
        refreshToken.setToken("token-123");
        refreshToken.setExpiryDate(expiryDate);
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
                expiryDate,
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

    @Test
    void allArgsConstructor_shouldWork() {

        User user =
                new User();

        LocalDateTime expiryDate =
                LocalDateTime.now();

        RefreshToken refreshToken =
                new RefreshToken(
                        1L,
                        "refresh-token",
                        expiryDate,
                        user
                );

        assertEquals(
                1L,
                refreshToken.getId()
        );

        assertEquals(
                "refresh-token",
                refreshToken.getToken()
        );

        assertEquals(
                expiryDate,
                refreshToken.getExpiryDate()
        );

        assertEquals(
                user,
                refreshToken.getUser()
        );
    }
}