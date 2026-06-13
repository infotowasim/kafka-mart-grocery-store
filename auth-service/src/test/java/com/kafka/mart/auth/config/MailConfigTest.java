package com.kafka.mart.auth.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailConfigTest {

    @Test
    void shouldCreateMailConfig() {

        MailConfig config =
                new MailConfig();

        assertNotNull(
                config
        );
    }
}