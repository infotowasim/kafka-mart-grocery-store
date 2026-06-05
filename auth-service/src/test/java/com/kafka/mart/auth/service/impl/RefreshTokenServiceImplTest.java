package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.RefreshToken;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenServiceImpl;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(
                refreshTokenServiceImpl,
                "refreshExpiration",
                86400000L
        );
    }



    @Test
    void createRefreshTokenNew() {

        User user = new User();

        when(refreshTokenRepository.findByUser(user))
                .thenReturn(Optional.empty());

        when(refreshTokenRepository.save(
                any(RefreshToken.class)
        )).thenAnswer(i -> i.getArgument(0));

        RefreshToken result =
                refreshTokenServiceImpl
                        .createRefreshToken(user);

        assertNotNull(result);

        assertNotNull(result.getToken());

        verify(refreshTokenRepository)
                .save(any(RefreshToken.class));
    }



    @Test
    void createRefreshTokenExisting() {

        User user = new User();

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .user(user)
                        .build();

        when(refreshTokenRepository.findByUser(user))
                .thenReturn(
                        Optional.of(refreshToken)
                );

        when(refreshTokenRepository.save(
                refreshToken
        )).thenReturn(refreshToken);

        RefreshToken result =
                refreshTokenServiceImpl
                        .createRefreshToken(user);

        assertNotNull(result);

        assertNotNull(result.getToken());

        verify(refreshTokenRepository)
                .save(refreshToken);
    }



    @Test
    void verifyRefreshTokenInvalid() {

        when(refreshTokenRepository.findByToken(
                "invalid"
        )).thenReturn(Optional.empty());

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> refreshTokenServiceImpl
                                .verifyRefreshToken(
                                        "invalid"
                                )
                );

        assertEquals(
                "Invalid refresh token",
                exception.getMessage()
        );
    }



    @Test
    void verifyRefreshTokenExpired() {

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token("token")
                        .expiryDate(
                                LocalDateTime.now()
                                        .minusMinutes(1)
                        )
                        .build();

        when(refreshTokenRepository.findByToken(
                "token"
        )).thenReturn(
                Optional.of(refreshToken)
        );

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> refreshTokenServiceImpl
                                .verifyRefreshToken(
                                        "token"
                                )
                );

        assertEquals(
                "Refresh token expired",
                exception.getMessage()
        );
    }



    @Test
    void verifyRefreshTokenSuccess() {

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token("token")
                        .expiryDate(
                                LocalDateTime.now()
                                        .plusMinutes(5)
                        )
                        .build();

        when(refreshTokenRepository.findByToken(
                "token"
        )).thenReturn(
                Optional.of(refreshToken)
        );

        RefreshToken result =
                refreshTokenServiceImpl
                        .verifyRefreshToken(
                                "token"
                        );

        assertNotNull(result);

        assertEquals(
                "token",
                result.getToken()
        );
    }


 }