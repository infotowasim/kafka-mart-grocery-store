package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.RefreshToken;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.repository.RefreshTokenRepository;
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
    private RefreshTokenServiceImpl refreshTokenService;

    @Test
    void createRefreshToken_shouldCreateNewToken() {

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        ReflectionTestUtils.setField(
                refreshTokenService,
                "refreshExpiration",
                86400000L
        );

        when(refreshTokenRepository.findByUser(user))
                .thenReturn(Optional.empty());

        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken result =
                refreshTokenService.createRefreshToken(user);

        assertNotNull(result);

        assertNotNull(result.getToken());

        assertNotNull(result.getExpiryDate());

        assertEquals(
                user,
                result.getUser()
        );

        verify(refreshTokenRepository)
                .save(any(RefreshToken.class));
    }

    @Test
    void createRefreshToken_shouldUpdateExistingToken() {

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        RefreshToken existingToken =
                RefreshToken.builder()
                        .id(1L)
                        .user(user)
                        .token("old-token")
                        .build();

        ReflectionTestUtils.setField(
                refreshTokenService,
                "refreshExpiration",
                86400000L
        );

        when(refreshTokenRepository.findByUser(user))
                .thenReturn(Optional.of(existingToken));

        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken result =
                refreshTokenService.createRefreshToken(user);

        assertNotEquals(
                "old-token",
                result.getToken()
        );

        verify(refreshTokenRepository)
                .save(existingToken);
    }

    @Test
    void verifyRefreshToken_shouldReturnToken() {

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token("valid-token")
                        .expiryDate(
                                LocalDateTime.now().plusDays(1)
                        )
                        .build();

        when(refreshTokenRepository.findByToken("valid-token"))
                .thenReturn(Optional.of(refreshToken));

        RefreshToken result =
                refreshTokenService.verifyRefreshToken(
                        "valid-token"
                );

        assertEquals(
                refreshToken,
                result
        );
    }

    @Test
    void verifyRefreshToken_shouldThrowExceptionWhenTokenNotFound() {

        when(refreshTokenRepository.findByToken("invalid-token"))
                .thenReturn(Optional.empty());

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> refreshTokenService.verifyRefreshToken(
                                "invalid-token"
                        )
                );

        assertEquals(
                "Invalid refresh token",
                exception.getMessage()
        );
    }

    @Test
    void verifyRefreshToken_shouldThrowExceptionWhenExpired() {

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token("expired-token")
                        .expiryDate(
                                LocalDateTime.now().minusDays(1)
                        )
                        .build();

        when(refreshTokenRepository.findByToken("expired-token"))
                .thenReturn(Optional.of(refreshToken));

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> refreshTokenService.verifyRefreshToken(
                                "expired-token"
                        )
                );

        assertEquals(
                "Refresh token expired",
                exception.getMessage()
        );
    }

    @Test
    void deleteRefreshToken_shouldDeleteToken() {

        refreshTokenService.deleteRefreshToken(
                "test-token"
        );

        verify(refreshTokenRepository)
                .deleteByToken("test-token");
    }
}