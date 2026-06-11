package com.kafka.mart.auth.controller;

import com.kafka.mart.auth.dto.request.OtpRequest;
import com.kafka.mart.auth.dto.request.VerifyOtpRequest;
import com.kafka.mart.auth.payload.ApiSuccessPayload;
import com.kafka.mart.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final AuthService authService;




    @PostMapping("/verify-otp")
    public ApiSuccessPayload verifyOtp(
            @RequestBody VerifyOtpRequest request
    ) {
        return authService.verifyOtp(request);
    }

    @PostMapping("/resend-otp")
    public ApiSuccessPayload resendOtp(
            @RequestBody OtpRequest request
    ) {
        return authService.resendOtp(request);
    }
}