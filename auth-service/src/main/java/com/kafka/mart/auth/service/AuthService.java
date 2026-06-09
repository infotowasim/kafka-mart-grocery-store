package com.kafka.mart.auth.service;

import com.kafka.mart.auth.dto.request.*;
import com.kafka.mart.auth.dto.response.LoginResponse;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.payload.ApiSuccessPayload;

public interface AuthService {

    ApiSuccessPayload  register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getCurrentUser();

    LoginResponse refreshToken(RefreshTokenRequest request);

    ApiSuccessPayload  logout(RefreshTokenRequest request);



    ApiSuccessPayload  sendOtp(OtpRequest request);

    ApiSuccessPayload  verifyOtp(VerifyOtpRequest request);

    ApiSuccessPayload resendOtp(OtpRequest request);
}