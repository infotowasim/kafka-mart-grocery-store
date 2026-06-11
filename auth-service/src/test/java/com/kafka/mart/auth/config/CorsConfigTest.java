package com.kafka.mart.auth.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.filter.CorsFilter;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    private final CorsConfig corsConfig =
            new CorsConfig();

    @Test
    void corsFilter_shouldCreateBean() {

        CorsFilter corsFilter =
                corsConfig.corsFilter();

        assertNotNull(corsFilter);
    }
}