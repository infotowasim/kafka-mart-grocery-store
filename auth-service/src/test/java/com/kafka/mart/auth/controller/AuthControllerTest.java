package com.kafka.mart.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.mart.auth.dto.request.*;
import com.kafka.mart.auth.dto.response.ApiResponse;
import com.kafka.mart.auth.dto.response.LoginResponse;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.security.JwtFilter;
import com.kafka.mart.auth.security.JwtService;
import com.kafka.mart.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private AuthService authService;




    @Test
    void registerSuccess() throws Exception {

        RegisterRequest request =
                new RegisterRequest();

        request.setFirstName("Wasim");
        request.setLastName("Akram");
        request.setEmail("test@gmail.com");
        request.setPassword("123456");
        request.setPhone("9999999999");

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("Registration successful")
                        .build();

        when(authService.register(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.success")
                                .value(true)
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "Registration successful"
                                )
                );
    }



    @Test
    void loginSuccess() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("123456");

        LoginResponse response =
                LoginResponse.builder()
                        .accessToken("access-token")
                        .refreshToken("refresh-token")
                        .tokenType("Bearer")
                        .build();

        when(authService.login(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken")
                        .value("access-token"));
    }



    @Test
    void sendOtpSuccess() throws Exception {

        OtpRequest request = new OtpRequest();
        request.setEmail("test@gmail.com");

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("OTP sent")
                        .build();

        when(authService.sendOtp(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/send-otp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success")
                        .value(true));
    }



    @Test
    void verifyOtpSuccess() throws Exception {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail("test@gmail.com");
        request.setOtp("123456");

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("OTP verified")
                        .build();

        when(authService.verifyOtp(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/verify-otp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success")
                        .value(true));
    }




    @Test
    void forgotPasswordSuccess() throws Exception {

        ForgotPasswordRequest request =
                new ForgotPasswordRequest();

        request.setEmail("test@gmail.com");

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("Reset link sent")
                        .build();

        when(authService.forgotPassword(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/forgot-password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk());
    }



    @Test
    void resetPasswordSuccess() throws Exception {

        ResetPasswordRequest request =
                new ResetPasswordRequest();

        request.setEmail("test@gmail.com");
        request.setNewPassword("newPassword123");

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("Password reset successful")
                        .build();

        when(authService.resetPassword(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/reset-password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }



    @Test
    void changePasswordSuccess() throws Exception {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setEmail("test@gmail.com");
        request.setOldPassword("old123");
        request.setNewPassword("new123");

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("Password changed successfully")
                        .build();

        when(authService.changePassword(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/change-password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }



    @Test
    void getCurrentUserSuccess() throws Exception {

        UserResponse response =
                UserResponse.builder()
                        .id(1L)
                        .firstName("Wasim")
                        .lastName("Akram")
                        .email("test@gmail.com")
                        .phone("9999999999")
                        .role("USER")
                        .build();

        when(authService.getCurrentUser())
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/auth/me")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("test@gmail.com"));
    }



    @Test
    void resendOtpSuccess() throws Exception {

        OtpRequest request =
                new OtpRequest();

        request.setEmail("test@gmail.com");

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("OTP resent")
                        .build();

        when(authService.resendOtp(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/resend-otp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }



    @Test
    void refreshTokenSuccess() throws Exception {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken("refresh-token");

        LoginResponse response =
                LoginResponse.builder()
                        .accessToken("new-access-token")
                        .refreshToken("refresh-token")
                        .tokenType("Bearer")
                        .build();

        when(authService.refreshToken(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/refresh-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken")
                        .value("new-access-token"));
    }



    @Test
    void logoutSuccess() throws Exception {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken("refresh-token");

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("Logout successful")
                        .build();

        when(authService.logout(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success")
                        .value(true));
    }











}