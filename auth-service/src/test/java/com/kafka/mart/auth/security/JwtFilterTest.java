package com.kafka.mart.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {

        jwtFilter =
                new JwtFilter(
                        jwtService,
                        userDetailsService
                );

        SecurityContextHolder.clearContext();
    }

    @Test
    void noAuthorizationHeader() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn(null);

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    void invalidBearerHeader() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Invalid");

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    void validTokenSuccess() throws Exception {

        String token = "jwt-token";

        User user =
                new User(
                        "test@gmail.com",
                        "123456",
                        List.of()
                );

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token))
                .thenReturn("test@gmail.com");

        when(userDetailsService.loadUserByUsername(
                "test@gmail.com"
        )).thenReturn(user);

        when(jwtService.isTokenValid(
                token,
                user
        )).thenReturn(true);

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(request, response);
    }

    @Test
    void invalidToken() throws Exception {

        String token = "jwt-token";

        User user =
                new User(
                        "test@gmail.com",
                        "123456",
                        List.of()
                );

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token))
                .thenReturn("test@gmail.com");

        when(userDetailsService.loadUserByUsername(
                "test@gmail.com"
        )).thenReturn(user);

        when(jwtService.isTokenValid(
                token,
                user
        )).thenReturn(false);

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(request, response);
    }



    @Test
    void userEmailNull() throws Exception {

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token");

        when(jwtService.extractUsername("token"))
                .thenReturn(null);

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(request, response);
    }



    @Test
    void authenticationAlreadyExists() throws Exception {

        SecurityContextHolder.getContext()
                .setAuthentication(
                        mock(
                                UsernamePasswordAuthenticationToken.class
                        )
                );

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token");

        when(jwtService.extractUsername("token"))
                .thenReturn("test@gmail.com");

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(request, response);

        SecurityContextHolder.clearContext();
    }
}