package com.kafka.mart.auth.service;

import com.kafka.mart.auth.dto.request.ChangePasswordRequest;
import com.kafka.mart.auth.dto.request.ForgotPasswordRequest;
import com.kafka.mart.auth.dto.request.ResetPasswordByTokenRequest;
import com.kafka.mart.auth.payload.ApiSuccessPayload;

public interface PasswordService {

    ApiSuccessPayload forgotPassword(
            ForgotPasswordRequest request
    );

    ApiSuccessPayload resetPasswordByToken(
            ResetPasswordByTokenRequest request
    );

    ApiSuccessPayload changePassword(
            ChangePasswordRequest request
    );
}