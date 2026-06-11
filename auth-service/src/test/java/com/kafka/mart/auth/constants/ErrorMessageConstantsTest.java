package com.kafka.mart.auth.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageConstantsTest {

    @Test
    void constants_shouldHaveExpectedValues() {

        assertEquals(
                "User not found",
                ErrorMessageConstants.USER_NOT_FOUND
        );

        assertEquals(
                "Email already exists",
                ErrorMessageConstants.EMAIL_ALREADY_EXISTS
        );

        assertEquals(
                "Phone already exists",
                ErrorMessageConstants.PHONE_ALREADY_EXISTS
        );

        assertEquals(
                "OTP not found",
                ErrorMessageConstants.OTP_NOT_FOUND
        );

        assertEquals(
                "Invalid OTP",
                ErrorMessageConstants.INVALID_OTP
        );

        assertEquals(
                "OTP expired",
                ErrorMessageConstants.OTP_EXPIRED
        );

        assertEquals(
                "Invalid token",
                ErrorMessageConstants.INVALID_TOKEN
        );

        assertEquals(
                "Token expired",
                ErrorMessageConstants.TOKEN_EXPIRED
        );

        assertEquals(
                "Invalid refresh token",
                ErrorMessageConstants.INVALID_REFRESH_TOKEN
        );

        assertEquals(
                "Refresh token expired",
                ErrorMessageConstants.REFRESH_TOKEN_EXPIRED
        );

        assertEquals(
                "Old password is incorrect",
                ErrorMessageConstants.INVALID_OLD_PASSWORD
        );

        assertEquals(
                "Account is locked",
                ErrorMessageConstants.ACCOUNT_LOCKED
        );

        assertEquals(
                "Please verify your email first",
                ErrorMessageConstants.EMAIL_NOT_VERIFIED
        );
    }

    @Test
    void constructor_shouldBeCovered()
            throws Exception {

        Constructor<ErrorMessageConstants> constructor =
                ErrorMessageConstants.class
                        .getDeclaredConstructor();

        constructor.setAccessible(true);

        ErrorMessageConstants instance =
                constructor.newInstance();

        assertNotNull(instance);
    }
}