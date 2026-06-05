package com.kafka.mart.auth.controller;

import com.kafka.mart.auth.dto.request.*;
import com.kafka.mart.auth.dto.response.ApiResponse;
import com.kafka.mart.auth.dto.response.LoginResponse;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtp(
            @Valid @RequestBody OtpRequest request) {

        return ResponseEntity.ok(
                authService.sendOtp(request)
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        return ResponseEntity.ok(
                authService.verifyOtp(request)
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        return ResponseEntity.ok(
                authService.forgotPassword(request)
        );
    }


    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        return ResponseEntity.ok(
                authService.resetPassword(request)
        );
    }


    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @RequestBody ChangePasswordRequest request
    ) {
        return ResponseEntity.ok(
                authService.changePassword(request)
        );
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {

        return ResponseEntity.ok(
                authService.getCurrentUser()
        );
    }


    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse> resendOtp(
            @RequestBody OtpRequest request
    ) {

        return ResponseEntity.ok(
                authService.resendOtp(request)
        );
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {

        return ResponseEntity.ok(
                authService.refreshToken(request)
        );
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
            @RequestBody RefreshTokenRequest request
    ) {

        return ResponseEntity.ok(
                authService.logout(request)
        );
    }



}