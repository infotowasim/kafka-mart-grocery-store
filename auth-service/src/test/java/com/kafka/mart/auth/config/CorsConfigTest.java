package com.kafka.mart.auth.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.filter.CorsFilter;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    @Test
    void corsFilterSuccess() {

        CorsConfig config =
                new CorsConfig();

        CorsFilter corsFilter =
                config.corsFilter();

        assertNotNull(corsFilter);
    }
}