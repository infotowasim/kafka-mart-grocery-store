package com.kafka.mart.auth.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiSuccessPayload {

    private String message;

    private Object data;
}