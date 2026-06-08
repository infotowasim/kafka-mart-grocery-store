package com.kafka.mart.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordByTokenRequest {

    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;
}