package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.MailException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;


    @Async
    @Override
    public void sendOtpEmail(
            String to,
            String otp
    ) {

        log.info(
                "Sending OTP email to {}",
                to
        );

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("OTP Verification");

        message.setText(
                "Your OTP is: "
                        + otp
                        + "\nValid for 5 minutes."
        );

        try {

            mailSender.send(message);

            log.info(
                    "OTP email sent successfully to : {}",
                    to
            );

        } catch (MailException ex) {

            log.error(
                    "Failed to send OTP email to : {}",
                    to,
                    ex
            );
        }

    }



    @Async
    @Override
    public void sendPasswordResetEmail(
            String to,
            String token
    ) {

        log.info(
                "Sending password reset email to {}",
                to
        );

        String resetLink =
                "http://localhost:3000/reset-password?token="
                        + token;

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);

        message.setSubject(
                "Password Reset"
        );

        message.setText(
                "Click below link:\n\n"
                        + resetLink
        );


        try {

            mailSender.send(message);

            log.info(
                    "Password reset email sent successfully to : {}",
                    to
            );

        } catch (MailException ex) {

            log.error(
                    "Failed to send password reset email to : {}",
                    to,
                    ex
            );
        }

    }



}