package com.kafka.mart.auth.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestDtoTest {

    @Test
    void changePasswordRequestSuccess() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setEmail("test@gmail.com");
        request.setOldPassword("old");
        request.setNewPassword("new");

        assertEquals(
                "test@gmail.com",
                request.getEmail()
        );

        assertEquals(
                "old",
                request.getOldPassword()
        );

        assertEquals(
                "new",
                request.getNewPassword()
        );
    }

    @Test
    void forgotPasswordRequestSuccess() {

        ForgotPasswordRequest request =
                new ForgotPasswordRequest();

        request.setEmail("test@gmail.com");

        assertEquals(
                "test@gmail.com",
                request.getEmail()
        );
    }

    @Test
    void loginRequestSuccess() {

        LoginRequest request =
                new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("password");

        assertEquals(
                "test@gmail.com",
                request.getEmail()
        );

        assertEquals(
                "password",
                request.getPassword()
        );
    }

    @Test
    void otpRequestSuccess() {

        OtpRequest request =
                new OtpRequest();

        request.setEmail("test@gmail.com");

        assertEquals(
                "test@gmail.com",
                request.getEmail()
        );
    }

    @Test
    void refreshTokenRequestSuccess() {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken("token");

        assertEquals(
                "token",
                request.getRefreshToken()
        );
    }

    @Test
    void registerRequestSuccess() {

        RegisterRequest request =
                new RegisterRequest();

        request.setFirstName("Wasim");
        request.setLastName("Akram");
        request.setEmail("test@gmail.com");
        request.setPhone("9999999999");
        request.setPassword("password");

        assertEquals(
                "Wasim",
                request.getFirstName()
        );

        assertEquals(
                "Akram",
                request.getLastName()
        );

        assertEquals(
                "test@gmail.com",
                request.getEmail()
        );
    }

    @Test
    void resetPasswordRequestSuccess() {

        ResetPasswordRequest request =
                new ResetPasswordRequest();

        request.setEmail("test@gmail.com");
        request.setNewPassword("newPassword");

        assertEquals(
                "test@gmail.com",
                request.getEmail()
        );

        assertEquals(
                "newPassword",
                request.getNewPassword()
        );
    }

    @Test
    void verifyOtpRequestSuccess() {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail("test@gmail.com");
        request.setOtp("123456");

        assertEquals(
                "test@gmail.com",
                request.getEmail()
        );

        assertEquals(
                "123456",
                request.getOtp()
        );
    }
}