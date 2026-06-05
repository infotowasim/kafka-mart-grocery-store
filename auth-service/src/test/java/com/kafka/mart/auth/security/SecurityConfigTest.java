package com.kafka.mart.auth.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtFilter jwtFilter;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void authenticationProviderSuccess() {

        AuthenticationProvider provider =
                securityConfig.authenticationProvider();

        assertNotNull(provider);
        assertTrue(provider instanceof org.springframework.security.authentication.dao.DaoAuthenticationProvider);
    }

    @Test
    void authenticationManagerSuccess() throws Exception {

        when(authenticationConfiguration.getAuthenticationManager())
                .thenReturn(authenticationManager);

        AuthenticationManager manager =
                securityConfig.authenticationManager(authenticationConfiguration);

        assertNotNull(manager);
        assertEquals(authenticationManager, manager);
    }

    @Test
    void securityFilterChainSuccess() throws Exception {

        HttpSecurity http =
                mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        DefaultSecurityFilterChain filterChain =
                mock(DefaultSecurityFilterChain.class);

        when(http.build())
                .thenReturn(filterChain);

        SecurityFilterChain result =
                securityConfig.securityFilterChain(http);

        assertNotNull(result);
    }
}