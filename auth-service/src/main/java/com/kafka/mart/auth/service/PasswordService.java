package com.kafka.mart.auth.service;

import com.kafka.mart.auth.dto.request.ChangePasswordRequest;
import com.kafka.mart.auth.dto.request.ForgotPasswordRequest;
import com.kafka.mart.auth.dto.request.ResetPasswordByTokenRequest;
import com.kafka.mart.auth.dto.response.ApiResponse;

public interface PasswordService {

    ApiResponse forgotPassword(
            ForgotPasswordRequest request
    );

    ApiResponse resetPasswordByToken(
            ResetPasswordByTokenRequest request
    );

    ApiResponse changePassword(
            ChangePasswordRequest request
    );
}