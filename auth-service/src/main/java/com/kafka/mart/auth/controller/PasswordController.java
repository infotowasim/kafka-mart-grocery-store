package com.kafka.mart.auth.controller;

import com.kafka.mart.auth.dto.request.ChangePasswordRequest;
import com.kafka.mart.auth.dto.request.ForgotPasswordRequest;
import com.kafka.mart.auth.dto.request.ResetPasswordByTokenRequest;
import com.kafka.mart.auth.dto.response.ApiResponse;
import com.kafka.mart.auth.service.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        "/api/v1/password"
)
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService
            passwordService;

    @PostMapping(
            "/forgot-password"
    )
    public ApiResponse forgotPassword(
            @Valid
            @RequestBody
            ForgotPasswordRequest request
    ) {

        return passwordService
                .forgotPassword(
                        request
                );
    }

    @PostMapping(
            "/reset-password"
    )
    public ApiResponse resetPassword(
            @Valid
            @RequestBody
            ResetPasswordByTokenRequest request
    ) {

        return passwordService
                .resetPasswordByToken(
                        request
                );
    }

    @PostMapping(
            "/change-password"
    )
    public ApiResponse changePassword(
            @Valid
            @RequestBody
            ChangePasswordRequest request
    ) {

        return passwordService
                .changePassword(
                        request
                );
    }
}