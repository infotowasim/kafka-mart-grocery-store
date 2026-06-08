package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtpEmail(
            String to,
            String otp
    ) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("OTP Verification");

        message.setText(
                "Your OTP is: "
                        + otp
                        + "\nValid for 5 minutes."
        );

        mailSender.send(message);
    }



    @Override
    public void sendPasswordResetEmail(
            String to,
            String token
    ) {

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

        mailSender.send(message);
    }
}