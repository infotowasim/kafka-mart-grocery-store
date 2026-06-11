package com.kafka.mart.auth.service;

import com.kafka.mart.auth.entity.RefreshToken;
import com.kafka.mart.auth.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(
            User user
    );

    RefreshToken verifyRefreshToken(
            String token
    );

    void deleteRefreshToken(String token);
}