package com.kafka.mart.auth.payload;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorPayload {

    private boolean success;

    private String message;

    private String errorCode;

    private LocalDateTime timestamp;
}