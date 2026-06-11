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

        LocalDateTime createdAt =
                LocalDateTime.now();

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .id(1L)
                        .otp("123456")
                        .expiryTime(expiryTime)
                        .verified(true)
                        .attemptCount(2)
                        .createdAt(createdAt)
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
                createdAt,
                otpVerification.getCreatedAt()
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
        otpVerification.setCreatedAt(now);
        otpVerification.setUser(user);

        assertEquals(2L, otpVerification.getId());
        assertEquals("654321", otpVerification.getOtp());
        assertEquals(now, otpVerification.getExpiryTime());
        assertTrue(otpVerification.getVerified());
        assertEquals(5, otpVerification.getAttemptCount());
        assertEquals(now, otpVerification.getCreatedAt());
        assertEquals(user, otpVerification.getUser());
    }

    @Test
    void noArgsConstructor_shouldWork() {

        OtpVerification otpVerification =
                new OtpVerification();

        assertNotNull(
                otpVerification
        );
    }

    @Test
    void allArgsConstructor_shouldWork() {

        User user = new User();

        LocalDateTime now =
                LocalDateTime.now();

        OtpVerification otpVerification =
                new OtpVerification(
                        1L,
                        "999999",
                        now,
                        true,
                        3,
                        now,
                        user
                );

        assertEquals(1L, otpVerification.getId());
        assertEquals("999999", otpVerification.getOtp());
        assertEquals(now, otpVerification.getExpiryTime());
        assertTrue(otpVerification.getVerified());
        assertEquals(3, otpVerification.getAttemptCount());
        assertEquals(now, otpVerification.getCreatedAt());
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

        assertNotNull(
                otpVerification.getCreatedAt()
        );
    }
}