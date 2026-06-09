package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.OtpVerification;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.repository.OtpVerificationRepository;
import com.kafka.mart.auth.service.OtpService;
import com.kafka.mart.auth.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.kafka.mart.auth.util.OtpGenerator;


@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl
        implements OtpService {

    private final OtpVerificationRepository otpRepository;



    @Override
    public String generateOtp() {

        return OtpGenerator.generateOtp();
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

        log.info(
                "OTP saved for user : {}",
                user.getEmail()
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

            log.warn(
                    "OTP not found for user : {}",
                    user.getEmail()
            );

            return false;
        }



        if (
                otpVerification
                        .getExpiryTime()
                        .isBefore(
                                DateTimeUtil.now()
                        )
        ) {

            log.warn(
                    "OTP expired for user : {}",
                    user.getEmail()
            );

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

            log.warn(
                    "Invalid OTP attempt for user : {}",
                    user.getEmail()
            );

            return false;


        }

        otpVerification.setVerified(
                true
        );

        otpRepository.save(
                otpVerification
        );

        log.info(
                "OTP verified successfully for user : {}",
                user.getEmail()
        );

        return true;
    }
}