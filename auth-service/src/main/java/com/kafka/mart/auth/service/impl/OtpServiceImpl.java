package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.OtpVerification;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.repository.OtpVerificationRepository;
import com.kafka.mart.auth.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = BadRequestException.class)
public class OtpServiceImpl implements OtpService {

    private final OtpVerificationRepository otpRepository;

    @Override
    public String generateOtp() {

        return String.format(
                "%06d",
                new Random().nextInt(999999)
        );
    }

    @Override
    public void saveOtp(User user, String otp) {

        OtpVerification otpVerification =
                otpRepository.findByUser(user)
                        .orElse(
                                OtpVerification.builder()
                                        .user(user)
                                        .build()
                        );

        otpVerification.setOtp(otp);
        otpVerification.setExpiryTime(
                LocalDateTime.now().plusMinutes(5)
        );
        otpVerification.setVerified(false);
        otpVerification.setAttemptCount(0);
        otpRepository.save(otpVerification);
    }

    @Override
    public boolean verifyOtp(
            User user,
            String otp
    ) {

        OtpVerification otpVerification =
                otpRepository.findByUser(user)
                        .orElse(null);

        if (otpVerification == null) {
            return false;
        }

        if (otpVerification.getAttemptCount() >= 3) {
            throw new BadRequestException(
                    "OTP blocked. Maximum attempts exceeded."
            );
        }

        if (otpVerification.getVerified()) {
            return false;
        }

        if (otpVerification.getExpiryTime()
                .isBefore(LocalDateTime.now())) {
            return false;
        }

        if (!otpVerification.getOtp().equals(otp)) {

            otpVerification.setAttemptCount(
                    otpVerification.getAttemptCount() + 1
            );

            otpRepository.saveAndFlush(
                    otpVerification
            );

            System.out.println(
                    "AFTER SAVE = "
                            + otpVerification.getAttemptCount()
            );

            if (otpVerification.getAttemptCount() >= 3) {

                System.out.println(
                        "BLOCKING AT = "
                                + otpVerification.getAttemptCount()
                );

                throw new BadRequestException(
                        "OTP blocked. Maximum attempts exceeded."
                );
            }

            return false;
        }

        otpVerification.setAttemptCount(0);
        otpVerification.setVerified(true);

        otpRepository.saveAndFlush(
                otpVerification
        );

        return true;
    }
}