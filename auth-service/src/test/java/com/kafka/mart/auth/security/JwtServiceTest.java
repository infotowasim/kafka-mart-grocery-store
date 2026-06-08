package com.kafka.mart.auth.security;

import com.kafka.mart.auth.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {

        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secretKey",
                "mysecretkeymysecretkeymysecretkey123456"
        );

        ReflectionTestUtils.setField(
                jwtService,
                "jwtExpiration",
                3600000L
        );
    }

    @Test
    void generateTokenSuccess() {

        User user =
                new User(
                        "test@gmail.com",
                        "123456",
                        java.util.List.of()
                );

        String token =
                jwtService.generateToken(user);

        assertNotNull(token);
    }

    @Test
    void extractUsernameSuccess() {

        User user =
                new User(
                        "test@gmail.com",
                        "123456",
                        java.util.List.of()
                );

        String token =
                jwtService.generateToken(user);

        String username =
                jwtService.extractUsername(token);

        assertEquals(
                "test@gmail.com",
                username
        );
    }

    @Test
    void extractExpirationSuccess() {

        User user =
                new User(
                        "test@gmail.com",
                        "123456",
                        java.util.List.of()
                );

        String token =
                jwtService.generateToken(user);

        Date expiration =
                jwtService.extractExpiration(token);

        assertNotNull(expiration);
    }

    @Test
    void tokenValidSuccess() {

        User user =
                new User(
                        "test@gmail.com",
                        "123456",
                        java.util.List.of()
                );

        String token =
                jwtService.generateToken(user);

        assertTrue(
                jwtService.isTokenValid(
                        token,
                        user
                )
        );
    }

    @Test
    void tokenInvalidUser() {

        User tokenUser =
                new User(
                        "test@gmail.com",
                        "123456",
                        java.util.List.of()
                );

        User anotherUser =
                new User(
                        "other@gmail.com",
                        "123456",
                        java.util.List.of()
                );

        String token =
                jwtService.generateToken(tokenUser);

        assertFalse(
                jwtService.isTokenValid(
                        token,
                        anotherUser
                )
        );
    }
}