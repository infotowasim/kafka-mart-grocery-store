package com.kafka.mart.auth.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsyncConfigTest {

    @Test
    void shouldCreateAsyncConfig() {

        AsyncConfig config =
                new AsyncConfig();

        assertNotNull(
                config
        );
    }
}