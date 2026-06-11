package com.kafka.mart.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.mart.auth.dto.request.LoginRequest;
import com.kafka.mart.auth.dto.request.RefreshTokenRequest;
import com.kafka.mart.auth.dto.request.RegisterRequest;
import com.kafka.mart.auth.dto.response.LoginResponse;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.payload.ApiSuccessPayload;
import com.kafka.mart.auth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.kafka.mart.auth.security.filter.JwtFilter;
import com.kafka.mart.auth.security.handler.AccessDeniedHandlerImpl;
import com.kafka.mart.auth.security.service.CustomUserDetailsService;
import com.kafka.mart.auth.security.service.JwtService;
import com.kafka.mart.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void register_shouldReturn200() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Wasim");
        request.setLastName("Akram");
        request.setEmail("test@gmail.com");
        request.setPhone("9999999999");
        request.setPassword("password");

        ApiSuccessPayload response =
                ApiSuccessPayload.builder()
                        .success(true)
                        .message("Registration successful")
                        .build();

        when(authService.register(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    void login_shouldReturn200() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setUsername("test@gmail.com");
        request.setPassword("password");

        LoginResponse response =
                LoginResponse.builder()
                        .accessToken("token")
                        .refreshToken("refresh")
                        .tokenType("Bearer")
                        .build();

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    void getCurrentUser_shouldReturn200() throws Exception {

        UserResponse response =
                UserResponse.builder()
                        .email("test@gmail.com")
                        .build();

        when(authService.getCurrentUser())
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/auth/me")
                )
                .andExpect(status().isOk());
    }

    @Test
    void refreshToken_shouldReturn200() throws Exception {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken("refresh-token");

        LoginResponse response =
                LoginResponse.builder()
                        .accessToken("new-token")
                        .refreshToken("new-refresh")
                        .tokenType("Bearer")
                        .build();

        when(authService.refreshToken(any(RefreshTokenRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/refresh-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    void logout_shouldReturn200() throws Exception {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken("refresh-token");

        ApiSuccessPayload response =
                ApiSuccessPayload.builder()
                        .success(true)
                        .message("Logout successful")
                        .build();

        when(authService.logout(any(RefreshTokenRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }
}