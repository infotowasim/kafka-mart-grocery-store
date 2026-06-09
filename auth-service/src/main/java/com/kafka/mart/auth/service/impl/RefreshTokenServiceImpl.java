package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.RefreshToken;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.repository.RefreshTokenRepository;
import com.kafka.mart.auth.service.RefreshTokenService;
import com.kafka.mart.auth.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Slf4j
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
                DateTimeUtil.now()
                        .plusSeconds(
                                refreshExpiration / 1000
                        )
        );

        log.info(
                "Refresh token generated for user : {}",
                user.getEmail()
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
                        .orElse(null);

        if (refreshToken == null) {

            log.warn(
                    "Invalid refresh token used"
            );

            throw new BadRequestException(
                    "Invalid refresh token"
            );
        }



        if (
                refreshToken.getExpiryDate()
                        .isBefore(
                                DateTimeUtil.now()
                        )
        )
        {

            log.warn(
                    "Expired refresh token used"
            );

            throw new BadRequestException(
                    "Refresh token expired"
            );
        }

        return refreshToken;
    }
}