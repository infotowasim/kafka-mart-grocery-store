package com.kafka.mart.auth.mapper;

import com.kafka.mart.auth.entity.OtpVerification;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OtpMapperTest {

    private final OtpMapper otpMapper =
            Mappers.getMapper(OtpMapper.class);

    @Test
    void toEntitySuccess() {

        OtpVerification otpVerification =
                OtpVerification.builder()
                        .id(1L)
                        .otp("123456")
                        .expiryTime(
                                LocalDateTime.now()
                                        .plusMinutes(5)
                        )
                        .verified(false)
                        .attemptCount(0)
                        .build();

        OtpVerification result =
                otpMapper.toEntity(
                        otpVerification
                );

        assertNotNull(result);

        assertEquals(
                otpVerification.getId(),
                result.getId()
        );

        assertEquals(
                otpVerification.getOtp(),
                result.getOtp()
        );

        assertEquals(
                otpVerification.getExpiryTime(),
                result.getExpiryTime()
        );

        assertEquals(
                otpVerification.getVerified(),
                result.getVerified()
        );

        assertEquals(
                otpVerification.getAttemptCount(),
                result.getAttemptCount()
        );
    }
}