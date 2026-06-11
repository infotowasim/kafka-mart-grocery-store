package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.OtpVerification;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.repository.OtpVerificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OtpServiceImplTest {

    @Mock
    private OtpVerificationRepository otpRepository;

    @InjectMocks
    private OtpServiceImpl otpService;

    @Test
    void generateOtp_shouldGenerateOtp() {

        String otp = otpService.generateOtp();

        assertNotNull(otp);

        assertEquals(6, otp.length());
    }

    @Test
    void saveOtp_shouldCreateNewOtp() {

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        when(
                otpRepository.findByUser(user)
        ).thenReturn(Optional.empty());

        otpService.saveOtp(
                user,
                "123456"
        );

        verify(otpRepository)
                .save(any(OtpVerification.class));
    }

    @Test
    void saveOtp_shouldUpdateExistingOtp() {

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .user(user)
                        .otp("111111")
                        .build();

        when(
                otpRepository.findByUser(user)
        ).thenReturn(
                Optional.of(otpVerification)
        );

        otpService.saveOtp(
                user,
                "123456"
        );

        assertEquals(
                "123456",
                otpVerification.getOtp()
        );

        verify(otpRepository)
                .save(otpVerification);
    }

    @Test
    void verifyOtp_shouldReturnFalseWhenOtpNotFound() {

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        when(
                otpRepository.findByUser(user)
        ).thenReturn(Optional.empty());

        boolean result =
                otpService.verifyOtp(
                        user,
                        "123456"
                );

        assertFalse(result);
    }

    @Test
    void verifyOtp_shouldReturnFalseWhenOtpExpired() {

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .user(user)
                        .otp("123456")
                        .expiryTime(
                                LocalDateTime.now()
                                        .minusMinutes(1)
                        )
                        .build();

        when(
                otpRepository.findByUser(user)
        ).thenReturn(
                Optional.of(otpVerification)
        );

        boolean result =
                otpService.verifyOtp(
                        user,
                        "123456"
                );

        assertFalse(result);
    }

    @Test
    void verifyOtp_shouldReturnFalseWhenOtpInvalid() {

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .user(user)
                        .otp("123456")
                        .attemptCount(0)
                        .expiryTime(
                                LocalDateTime.now()
                                        .plusMinutes(5)
                        )
                        .build();

        when(
                otpRepository.findByUser(user)
        ).thenReturn(
                Optional.of(otpVerification)
        );

        boolean result =
                otpService.verifyOtp(
                        user,
                        "999999"
                );

        assertFalse(result);

        assertEquals(
                1,
                otpVerification.getAttemptCount()
        );

        verify(otpRepository)
                .save(otpVerification);
    }

    @Test
    void verifyOtp_shouldReturnTrueWhenOtpValid() {

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .user(user)
                        .otp("123456")
                        .verified(false)
                        .expiryTime(
                                LocalDateTime.now()
                                        .plusMinutes(5)
                        )
                        .build();

        when(
                otpRepository.findByUser(user)
        ).thenReturn(
                Optional.of(otpVerification)
        );

        boolean result =
                otpService.verifyOtp(
                        user,
                        "123456"
                );

        assertTrue(result);

        assertTrue(
                otpVerification.getVerified()
        );

        verify(otpRepository)
                .save(otpVerification);
    }
}