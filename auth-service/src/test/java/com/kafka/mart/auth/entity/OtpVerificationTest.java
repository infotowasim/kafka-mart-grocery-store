package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OtpVerificationTest {

    @Test
    void builderSuccess() {

        User user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        LocalDateTime expiryTime =
                LocalDateTime.now().plusMinutes(5);

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .id(1L)
                        .otp("123456")
                        .expiryTime(expiryTime)
                        .user(user)
                        .build();

        assertEquals(1L, otpVerification.getId());
        assertEquals("123456", otpVerification.getOtp());
        assertEquals(expiryTime, otpVerification.getExpiryTime());
        assertEquals(user, otpVerification.getUser());

        // Builder Default Values
        assertFalse(otpVerification.getVerified());
        assertEquals(0, otpVerification.getAttemptCount());
        assertNotNull(otpVerification.getCreatedAt());
    }

    @Test
    void getterSetterSuccess() {

        OtpVerification otpVerification =
                new OtpVerification();

        LocalDateTime expiryTime =
                LocalDateTime.now();

        LocalDateTime createdAt =
                LocalDateTime.now();

        otpVerification.setId(10L);
        otpVerification.setOtp("654321");
        otpVerification.setExpiryTime(expiryTime);
        otpVerification.setVerified(true);
        otpVerification.setAttemptCount(3);
        otpVerification.setCreatedAt(createdAt);

        assertEquals(10L, otpVerification.getId());
        assertEquals("654321", otpVerification.getOtp());
        assertEquals(expiryTime, otpVerification.getExpiryTime());
        assertTrue(otpVerification.getVerified());
        assertEquals(3, otpVerification.getAttemptCount());
        assertEquals(createdAt, otpVerification.getCreatedAt());
    }

    @Test
    void noArgsConstructorSuccess() {

        OtpVerification otpVerification =
                new OtpVerification();

        assertNotNull(otpVerification);
    }

    @Test
    void allArgsConstructorSuccess() {

        User user = new User();

        LocalDateTime expiryTime =
                LocalDateTime.now().plusMinutes(10);

        LocalDateTime createdAt =
                LocalDateTime.now();

        OtpVerification otpVerification =
                new OtpVerification(
                        1L,
                        "999999",
                        expiryTime,
                        true,
                        5,
                        createdAt,
                        user
                );

        assertEquals(1L, otpVerification.getId());
        assertEquals("999999", otpVerification.getOtp());
        assertEquals(expiryTime, otpVerification.getExpiryTime());
        assertTrue(otpVerification.getVerified());
        assertEquals(5, otpVerification.getAttemptCount());
        assertEquals(createdAt, otpVerification.getCreatedAt());
        assertEquals(user, otpVerification.getUser());
    }
}