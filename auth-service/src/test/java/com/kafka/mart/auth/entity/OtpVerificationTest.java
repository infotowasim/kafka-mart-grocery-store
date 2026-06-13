package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OtpVerificationTest {

    @Test
    void builder_shouldCreateOtpVerification() {

        User user = new User();

        LocalDateTime expiryTime =
                LocalDateTime.now().plusMinutes(5);

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .id(1L)
                        .otp("123456")
                        .expiryTime(expiryTime)
                        .verified(true)
                        .attemptCount(2)
                        .user(user)
                        .build();

        assertEquals(
                1L,
                otpVerification.getId()
        );

        assertEquals(
                "123456",
                otpVerification.getOtp()
        );

        assertEquals(
                expiryTime,
                otpVerification.getExpiryTime()
        );

        assertTrue(
                otpVerification.getVerified()
        );

        assertEquals(
                2,
                otpVerification.getAttemptCount()
        );

        assertEquals(
                user,
                otpVerification.getUser()
        );
    }


    @Test
    void settersAndGetters_shouldWork() {

        OtpVerification otpVerification =
                new OtpVerification();

        User user = new User();

        LocalDateTime now =
                LocalDateTime.now();

        otpVerification.setId(2L);
        otpVerification.setOtp("654321");
        otpVerification.setExpiryTime(now);
        otpVerification.setVerified(true);
        otpVerification.setAttemptCount(5);
        otpVerification.setUser(user);

        assertEquals(2L, otpVerification.getId());
        assertEquals("654321", otpVerification.getOtp());
        assertEquals(now, otpVerification.getExpiryTime());
        assertTrue(otpVerification.getVerified());
        assertEquals(5, otpVerification.getAttemptCount());
        assertEquals(user, otpVerification.getUser());
    }


    @Test
    void builderDefaultValues_shouldBeApplied() {

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .otp("111111")
                        .build();

        assertFalse(
                otpVerification.getVerified()
        );

        assertEquals(
                0,
                otpVerification.getAttemptCount()
        );
    }
}