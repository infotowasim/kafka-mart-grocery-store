package com.kafka.mart.auth.service;

import com.kafka.mart.auth.dto.request.*;
        import com.kafka.mart.auth.dto.response.ApiResponse;
import com.kafka.mart.auth.dto.response.LoginResponse;
import com.kafka.mart.auth.dto.response.UserResponse;

public interface AuthService {

    ApiResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    ApiResponse forgotPassword(ForgotPasswordRequest request);

    ApiResponse resetPassword(ResetPasswordRequest request);

    ApiResponse changePassword(ChangePasswordRequest request);

    UserResponse getCurrentUser();

    LoginResponse refreshToken(RefreshTokenRequest request);

    ApiResponse logout(RefreshTokenRequest request);

    ApiResponse resetPasswordByToken(ResetPasswordByTokenRequest request);


    ApiResponse sendOtp(OtpRequest request);

    ApiResponse verifyOtp(VerifyOtpRequest request);

    ApiResponse resendOtp(OtpRequest request);
}