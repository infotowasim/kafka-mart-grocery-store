package com.kafka.mart.auth.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {

        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secretKey",
                "mySecretKeyForJwtTesting123456789012345678901234567890"
        );

        ReflectionTestUtils.setField(
                jwtService,
                "jwtExpiration",
                86400000L
        );

        userDetails = new User(
                "test@gmail.com",
                "password",
                Collections.emptyList()
        );
    }

    @Test
    void generateToken_shouldGenerateToken() {

        String token =
                jwtService.generateToken(userDetails);

        assertNotNull(token);

        assertFalse(token.isBlank());
    }

    @Test
    void extractUsername_shouldReturnUsername() {

        String token =
                jwtService.generateToken(userDetails);

        String username =
                jwtService.extractUsername(token);

        assertEquals(
                "test@gmail.com",
                username
        );
    }

    @Test
    void extractExpiration_shouldReturnExpirationDate() {

        String token =
                jwtService.generateToken(userDetails);

        Date expiration =
                jwtService.extractExpiration(token);

        assertNotNull(expiration);

        assertTrue(
                expiration.after(new Date())
        );
    }

    @Test
    void extractClaim_shouldReturnSubject() {

        String token =
                jwtService.generateToken(userDetails);

        String subject =
                jwtService.extractClaim(
                        token,
                        claims -> claims.getSubject()
                );

        assertEquals(
                "test@gmail.com",
                subject
        );
    }

    @Test
    void isTokenValid_shouldReturnTrue() {

        String token =
                jwtService.generateToken(userDetails);

        boolean result =
                jwtService.isTokenValid(
                        token,
                        userDetails
                );

        assertTrue(result);
    }

    @Test
    void isTokenValid_shouldReturnFalseForDifferentUser() {

        String token =
                jwtService.generateToken(userDetails);

        UserDetails anotherUser =
                new User(
                        "another@gmail.com",
                        "password",
                        Collections.emptyList()
                );

        boolean result =
                jwtService.isTokenValid(
                        token,
                        anotherUser
                );

        assertFalse(result);
    }
}