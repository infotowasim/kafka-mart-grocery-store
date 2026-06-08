package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.OtpVerification;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.repository.OtpVerificationRepository;
import com.kafka.mart.auth.service.OtpService;
import com.kafka.mart.auth.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl
        implements OtpService {

    private final OtpVerificationRepository otpRepository;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generateOtp() {

        return String.format(
                "%06d",
                RANDOM.nextInt(1_000_000)
        );
    }



    @Override
    public void saveOtp(
            User user,
            String otp
    ) {

        OtpVerification otpVerification =
                otpRepository
                        .findByUser(user)
                        .orElse(
                                OtpVerification
                                        .builder()
                                        .user(user)
                                        .build()
                        );

        otpVerification.setOtp(otp);

        otpVerification.setExpiryTime(
                DateTimeUtil.now()
                        .plusMinutes(5)
        );

        otpVerification.setVerified(
                false
        );

        otpVerification.setAttemptCount(
                0
        );

        otpRepository.save(
                otpVerification
        );
    }

    @Override
    public boolean verifyOtp(
            User user,
            String otp
    ) {

        OtpVerification otpVerification =
                otpRepository
                        .findByUser(user)
                        .orElse(null);

        if (
                otpVerification == null
        ) {
            return false;
        }

        if (
                otpVerification
                        .getExpiryTime()
                        .isBefore(
                                DateTimeUtil.now()
                        )
        ) {
            return false;
        }

        if (
                !otpVerification
                        .getOtp()
                        .equals(otp)
        ) {

            otpVerification.setAttemptCount(
                    otpVerification.getAttemptCount() + 1
            );

            otpRepository.save(
                    otpVerification
            );

            return false;
        }

        otpVerification.setVerified(
                true
        );

        otpRepository.save(
                otpVerification
        );

        return true;
    }
}