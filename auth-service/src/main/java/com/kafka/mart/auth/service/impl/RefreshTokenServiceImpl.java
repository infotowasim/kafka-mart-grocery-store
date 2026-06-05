package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.RefreshToken;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.repository.RefreshTokenRepository;
import com.kafka.mart.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Override
    public RefreshToken createRefreshToken(
            User user
    ) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByUser(user)
                        .orElse(
                                RefreshToken.builder()
                                        .user(user)
                                        .build()
                        );

        refreshToken.setToken(
                UUID.randomUUID().toString()
        );

        refreshToken.setExpiryDate(
                LocalDateTime.now()
                        .plusSeconds(
                                refreshExpiration / 1000
                        )
        );

        return refreshTokenRepository.save(
                refreshToken
        );
    }

    @Override
    public RefreshToken verifyRefreshToken(
            String token
    ) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new BadRequestException(
                                        "Invalid refresh token"
                                ));

        if (
                refreshToken.getExpiryDate()
                        .isBefore(
                                LocalDateTime.now()
                        )
        ) {
            throw new BadRequestException(
                    "Refresh token expired"
            );
        }

        return refreshToken;
    }
}