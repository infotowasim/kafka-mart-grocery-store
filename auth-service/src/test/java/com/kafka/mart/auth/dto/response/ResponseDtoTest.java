package com.kafka.mart.auth.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseDtoTest {

    @Test
    void apiResponseBuilderSuccess() {

        ApiResponse response =
                ApiResponse.builder()
                        .success(true)
                        .message("Success")
                        .build();

        assertTrue(
                response.isSuccess()
        );

        assertEquals(
                "Success",
                response.getMessage()
        );
    }

    @Test
    void userResponseBuilderSuccess() {

        UserResponse response =
                UserResponse.builder()
                        .id(1L)
                        .firstName("Wasim")
                        .lastName("Akram")
                        .email("test@gmail.com")
                        .phone("9999999999")
                        .role("ROLE_ADMIN")
                        .build();

        assertEquals(
                1L,
                response.getId()
        );

        assertEquals(
                "Wasim",
                response.getFirstName()
        );

        assertEquals(
                "Akram",
                response.getLastName()
        );

        assertEquals(
                "ROLE_ADMIN",
                response.getRole()
        );
    }

    @Test
    void loginResponseBuilderSuccess() {

        UserResponse user =
                UserResponse.builder()
                        .id(1L)
                        .firstName("Wasim")
                        .build();

        LoginResponse response =
                LoginResponse.builder()
                        .accessToken("access-token")
                        .refreshToken("refresh-token")
                        .tokenType("Bearer")
                        .user(user)
                        .build();

        assertEquals(
                "access-token",
                response.getAccessToken()
        );

        assertEquals(
                "refresh-token",
                response.getRefreshToken()
        );

        assertEquals(
                "Bearer",
                response.getTokenType()
        );

        assertEquals(
                user,
                response.getUser()
        );
    }
}