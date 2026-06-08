package com.kafka.mart.auth.service;

public interface EmailService {

    void sendOtpEmail(
            String to,
            String otp
    );

    void sendPasswordResetEmail(
            String to,
            String token
    );
}