package com.kafka.mart.auth.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationPayload {

    private Integer page;

    private Integer size;

    private Long totalElements;

    private Integer totalPages;
}