package com.kafka.mart.auth.service;

import com.kafka.mart.auth.entity.User;

public interface OtpService {

    String generateOtp();

    void saveOtp(User user, String otp);

    boolean verifyOtp(User user, String otp);
}