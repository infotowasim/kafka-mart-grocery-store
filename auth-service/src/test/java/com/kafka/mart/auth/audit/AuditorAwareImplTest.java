package com.kafka.mart.auth.audit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuditorAwareImplTest {

    private final AuditorAwareImpl auditorAware =
            new AuditorAwareImpl();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnSystemWhenAuthenticationIsNull() {

        Optional<String> auditor =
                auditorAware.getCurrentAuditor();

        assertTrue(
                auditor.isPresent()
        );

        assertEquals(
                "SYSTEM",
                auditor.get()
        );
    }

    @Test
    void shouldReturnAuthenticatedUsername() {

        UsernamePasswordAuthenticationToken auth =
                UsernamePasswordAuthenticationToken.authenticated(
                        "wasim@gmail.com",
                        null,
                        java.util.List.of()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        Optional<String> auditor =
                auditorAware.getCurrentAuditor();

        assertTrue(
                auditor.isPresent()
        );

        assertEquals(
                "wasim@gmail.com",
                auditor.get()
        );
    }
}