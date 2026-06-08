package com.kafka.mart.auth.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorPayload {

    private String message;

    private String errorCode;
}