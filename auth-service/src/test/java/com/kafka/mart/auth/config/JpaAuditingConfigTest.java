package com.kafka.mart.auth.config;

import com.kafka.mart.auth.audit.AuditorAwareImpl;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.AuditorAware;

import static org.junit.jupiter.api.Assertions.*;

class JpaAuditingConfigTest {

    @Test
    void auditorProvider_shouldReturnAuditorAwareBean() {

        JpaAuditingConfig config =
                new JpaAuditingConfig();

        AuditorAware<String> auditorAware =
                config.auditorProvider();

        assertNotNull(
                auditorAware
        );

        assertInstanceOf(
                AuditorAwareImpl.class,
                auditorAware
        );
    }
}