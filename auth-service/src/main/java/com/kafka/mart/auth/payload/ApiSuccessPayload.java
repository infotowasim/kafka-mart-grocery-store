package com.kafka.mart.auth.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiSuccessPayload {

    private boolean success;

    private String message;

    private Object data;

    private PaginationPayload pagination;
}