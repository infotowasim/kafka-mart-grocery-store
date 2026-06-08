package com.kafka.mart.auth.controller;

import com.kafka.mart.auth.dto.request.OtpRequest;
import com.kafka.mart.auth.dto.request.VerifyOtpRequest;
import com.kafka.mart.auth.dto.response.ApiResponse;
import com.kafka.mart.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final AuthService authService;

    @PostMapping("/send-otp")
    public ApiResponse sendOtp(
            @RequestBody OtpRequest request
    ) {
        return authService.sendOtp(request);
    }

    @PostMapping("/verify-otp")
    public ApiResponse verifyOtp(
            @RequestBody VerifyOtpRequest request
    ) {
        return authService.verifyOtp(request);
    }

    @PostMapping("/resend-otp")
    public ApiResponse resendOtp(
            @RequestBody OtpRequest request
    ) {
        return authService.resendOtp(request);
    }
}