package com.kafka.mart.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.mart.auth.dto.request.OtpRequest;
import com.kafka.mart.auth.dto.request.VerifyOtpRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VerificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class VerificationControllerTest {

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
    void verifyOtp_shouldReturn200()
            throws Exception {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail(
                "test@gmail.com"
        );

        request.setOtp(
                "123456"
        );

        ApiSuccessPayload response =
                ApiSuccessPayload.builder()
                        .success(true)
                        .message("OTP verified successfully")
                        .build();

        when(
                authService.verifyOtp(
                        any(VerifyOtpRequest.class)
                )
        ).thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/verification/verify-otp")
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
    void resendOtp_shouldReturn200()
            throws Exception {

        OtpRequest request =
                new OtpRequest();

        request.setEmail(
                "test@gmail.com"
        );

        ApiSuccessPayload response =
                ApiSuccessPayload.builder()
                        .success(true)
                        .message("OTP resent successfully")
                        .build();

        when(
                authService.resendOtp(
                        any(OtpRequest.class)
                )
        ).thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/verification/resend-otp")
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