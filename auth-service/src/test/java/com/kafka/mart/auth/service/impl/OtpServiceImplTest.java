package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.OtpVerification;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.repository.OtpVerificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OtpServiceImplTest {

    @Mock
    private OtpVerificationRepository otpRepository;

    @InjectMocks
    private OtpServiceImpl otpServiceImpl;




    @Test
    void generateOtpSuccess() {

        String otp =
                otpServiceImpl.generateOtp();

        assertNotNull(otp);

        assertEquals(
                6,
                otp.length()
        );
    }



    @Test
    void saveOtpNewUser() {

        User user = new User();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.empty());

        otpServiceImpl.saveOtp(
                user,
                "123456"
        );

        verify(otpRepository)
                .save(any(OtpVerification.class));
    }



    @Test
    void saveOtpExistingUser() {

        User user = new User();

        OtpVerification otp =
                OtpVerification.builder()
                        .user(user)
                        .build();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.of(otp));

        otpServiceImpl.saveOtp(
                user,
                "123456"
        );

        verify(otpRepository)
                .save(otp);
    }



    @Test
    void verifyOtpNotFound() {

        User user = new User();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.empty());

        boolean result =
                otpServiceImpl.verifyOtp(
                        user,
                        "123456"
                );

        assertFalse(result);
    }



    @Test
    void verifyOtpBlocked() {

        User user = new User();

        OtpVerification otp =
                OtpVerification.builder()
                        .attemptCount(3)
                        .build();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.of(otp));

        assertThrows(
                BadRequestException.class,
                () -> otpServiceImpl.verifyOtp(
                        user,
                        "123456"
                )
        );
    }




    @Test
    void verifyOtpAlreadyVerified() {

        User user = new User();

        OtpVerification otp =
                OtpVerification.builder()
                        .verified(true)
                        .attemptCount(0)
                        .build();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.of(otp));

        boolean result =
                otpServiceImpl.verifyOtp(
                        user,
                        "123456"
                );

        assertFalse(result);
    }



    @Test
    void verifyOtpExpired() {

        User user = new User();

        OtpVerification otp =
                OtpVerification.builder()
                        .verified(false)
                        .attemptCount(0)
                        .expiryTime(
                                LocalDateTime.now()
                                        .minusMinutes(1)
                        )
                        .build();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.of(otp));

        boolean result =
                otpServiceImpl.verifyOtp(
                        user,
                        "123456"
                );

        assertFalse(result);
    }



    @Test
    void verifyOtpWrongOtp() {

        User user = new User();

        OtpVerification otp =
                OtpVerification.builder()
                        .otp("111111")
                        .verified(false)
                        .attemptCount(0)
                        .expiryTime(
                                LocalDateTime.now()
                                        .plusMinutes(5)
                        )
                        .build();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.of(otp));

        boolean result =
                otpServiceImpl.verifyOtp(
                        user,
                        "222222"
                );

        assertFalse(result);

        verify(otpRepository)
                .saveAndFlush(otp);
    }



    @Test
    void verifyOtpWrongOtpAndBlock() {

        User user = new User();

        OtpVerification otp =
                OtpVerification.builder()
                        .otp("111111")
                        .verified(false)
                        .attemptCount(2)
                        .expiryTime(
                                LocalDateTime.now()
                                        .plusMinutes(5)
                        )
                        .build();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.of(otp));

        assertThrows(
                BadRequestException.class,
                () -> otpServiceImpl.verifyOtp(
                        user,
                        "222222"
                )
        );
    }



    @Test
    void verifyOtpSuccess() {

        User user = new User();

        OtpVerification otp =
                OtpVerification.builder()
                        .otp("123456")
                        .verified(false)
                        .attemptCount(0)
                        .expiryTime(
                                LocalDateTime.now()
                                        .plusMinutes(5)
                        )
                        .build();

        when(otpRepository.findByUser(user))
                .thenReturn(Optional.of(otp));

        boolean result =
                otpServiceImpl.verifyOtp(
                        user,
                        "123456"
                );

        assertTrue(result);

        verify(otpRepository)
                .saveAndFlush(otp);
    }






























}