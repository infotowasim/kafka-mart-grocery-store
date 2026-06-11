package com.kafka.mart.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.mart.auth.dto.request.ChangePasswordRequest;
import com.kafka.mart.auth.dto.request.ForgotPasswordRequest;
import com.kafka.mart.auth.dto.request.ResetPasswordByTokenRequest;
import com.kafka.mart.auth.payload.ApiSuccessPayload;
import com.kafka.mart.auth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.kafka.mart.auth.security.filter.JwtFilter;
import com.kafka.mart.auth.security.handler.AccessDeniedHandlerImpl;
import com.kafka.mart.auth.security.service.CustomUserDetailsService;
import com.kafka.mart.auth.security.service.JwtService;
import com.kafka.mart.auth.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PasswordController.class)
@AutoConfigureMockMvc(addFilters = false)
class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PasswordService passwordService;

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
    void forgotPassword_shouldReturn200()
            throws Exception {

        ForgotPasswordRequest request =
                new ForgotPasswordRequest();

        request.setEmail(
                "test@gmail.com"
        );

        ApiSuccessPayload response =
                ApiSuccessPayload.builder()
                        .success(true)
                        .message(
                                "Password reset token generated"
                        )
                        .build();

        when(
                passwordService.forgotPassword(
                        any(ForgotPasswordRequest.class)
                )
        ).thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/password/forgot-password")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    void resetPasswordByToken_shouldReturn200()
            throws Exception {

        ResetPasswordByTokenRequest request =
                new ResetPasswordByTokenRequest();

        request.setToken(
                "reset-token"
        );

        request.setNewPassword(
                "NewPassword123"
        );

        ApiSuccessPayload response =
                ApiSuccessPayload.builder()
                        .success(true)
                        .message(
                                "Password reset successfully"
                        )
                        .build();

        when(
                passwordService.resetPasswordByToken(
                        any(
                                ResetPasswordByTokenRequest.class
                        )
                )
        ).thenReturn(response);

        mockMvc.perform(
                        post(
                                "/api/v1/password/reset-password-token"
                        )
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    void changePassword_shouldReturn200()
            throws Exception {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setOldPassword(
                "OldPassword123"
        );

        request.setNewPassword(
                "NewPassword123"
        );

        ApiSuccessPayload response =
                ApiSuccessPayload.builder()
                        .success(true)
                        .message(
                                "Password changed successfully"
                        )
                        .build();

        when(
                passwordService.changePassword(
                        any(
                                ChangePasswordRequest.class
                        )
                )
        ).thenReturn(response);

        mockMvc.perform(
                        post(
                                "/api/v1/password/change-password"
                        )
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(
                        status().isOk()
                );
    }
}