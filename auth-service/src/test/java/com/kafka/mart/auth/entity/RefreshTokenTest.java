package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenTest {

    @Test
    void builderSuccess() {

        User user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        LocalDateTime expiryDate =
                LocalDateTime.now().plusDays(7);

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .id(1L)
                        .token("refresh-token")
                        .expiryDate(expiryDate)
                        .user(user)
                        .build();

        assertEquals(1L, refreshToken.getId());
        assertEquals("refresh-token", refreshToken.getToken());
        assertEquals(expiryDate, refreshToken.getExpiryDate());
        assertEquals(user, refreshToken.getUser());
    }

    @Test
    void getterSetterSuccess() {

        RefreshToken refreshToken =
                new RefreshToken();

        LocalDateTime expiryDate =
                LocalDateTime.now();

        refreshToken.setId(10L);
        refreshToken.setToken("token-123");
        refreshToken.setExpiryDate(expiryDate);

        assertEquals(10L, refreshToken.getId());
        assertEquals("token-123", refreshToken.getToken());
        assertEquals(expiryDate, refreshToken.getExpiryDate());
    }

    @Test
    void noArgsConstructorSuccess() {

        RefreshToken refreshToken =
                new RefreshToken();

        assertNotNull(refreshToken);
    }

    @Test
    void allArgsConstructorSuccess() {

        User user = new User();

        LocalDateTime expiryDate =
                LocalDateTime.now().plusHours(5);

        RefreshToken refreshToken =
                new RefreshToken(
                        1L,
                        "token-value",
                        expiryDate,
                        user
                );

        assertEquals(1L, refreshToken.getId());
        assertEquals("token-value", refreshToken.getToken());
        assertEquals(expiryDate, refreshToken.getExpiryDate());
        assertEquals(user, refreshToken.getUser());
    }
}