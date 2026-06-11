package com.kafka.mart.auth.security.config;

import com.kafka.mart.auth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.kafka.mart.auth.security.filter.JwtFilter;
import com.kafka.mart.auth.security.handler.AccessDeniedHandlerImpl;
import com.kafka.mart.auth.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtFilter jwtFilter;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Mock
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void authenticationProvider_shouldReturnDaoAuthenticationProvider() {

        AuthenticationProvider provider =
                securityConfig.authenticationProvider();

        assertNotNull(provider);

        assertInstanceOf(
                DaoAuthenticationProvider.class,
                provider
        );
    }

    @Test
    void authenticationManager_shouldReturnManager()
            throws Exception {

        AuthenticationConfiguration configuration =
                mock(AuthenticationConfiguration.class);

        AuthenticationManager authenticationManager =
                mock(AuthenticationManager.class);

        when(
                configuration.getAuthenticationManager()
        ).thenReturn(authenticationManager);

        AuthenticationManager result =
                securityConfig.authenticationManager(
                        configuration
                );

        assertNotNull(result);

        assertEquals(
                authenticationManager,
                result
        );
    }
}