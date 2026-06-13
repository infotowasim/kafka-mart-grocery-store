package com.kafka.mart.auth.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumsTest {

    @Test
    void accountStatus_shouldContainExpectedValues() {

        assertEquals(
                3,
                AccountStatus.values().length
        );

        assertEquals(
                AccountStatus.ACTIVE,
                AccountStatus.valueOf("ACTIVE")
        );

        assertEquals(
                AccountStatus.LOCKED,
                AccountStatus.valueOf("LOCKED")
        );

        assertEquals(
                AccountStatus.DISABLED,
                AccountStatus.valueOf("DISABLED")
        );
    }

    @Test
    void authProvider_shouldContainExpectedValues() {

        assertEquals(
                2,
                AuthProvider.values().length
        );

        assertEquals(
                AuthProvider.LOCAL,
                AuthProvider.valueOf("LOCAL")
        );

        assertEquals(
                AuthProvider.GOOGLE,
                AuthProvider.valueOf("GOOGLE")
        );
    }

    @Test
    void roleType_shouldContainExpectedValues() {

        assertEquals(
                3,
                RoleType.values().length
        );

        assertEquals(
                RoleType.ROLE_ADMIN,
                RoleType.valueOf("ROLE_ADMIN")
        );

        assertEquals(
                RoleType.ROLE_CUSTOMER,
                RoleType.valueOf("ROLE_CUSTOMER")
        );

        assertEquals(
                RoleType.ROLE_DELIVERY,
                RoleType.valueOf("ROLE_DELIVERY")
        );
    }

    @Test
    void tokenType_shouldContainExpectedValues() {

        assertEquals(
                2,
                TokenType.values().length
        );

        assertEquals(
                TokenType.ACCESS_TOKEN,
                TokenType.valueOf("ACCESS_TOKEN")
        );

        assertEquals(
                TokenType.REFRESH_TOKEN,
                TokenType.valueOf("REFRESH_TOKEN")
        );
    }


    @Test
    void otpType_shouldContainExpectedValues() {

        assertEquals(
                2,
                OtpType.values().length
        );

        assertEquals(
                OtpType.EMAIL_VERIFICATION,
                OtpType.valueOf(
                        "EMAIL_VERIFICATION"
                )
        );

        assertEquals(
                OtpType.PASSWORD_RESET,
                OtpType.valueOf(
                        "PASSWORD_RESET"
                )
        );
    }
}