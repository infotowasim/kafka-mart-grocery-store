package com.kafka.mart.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtpGeneratorTest {

    @Test
    void constructorSuccess() {

        OtpGenerator otpGenerator =
                new OtpGenerator();

        assertNotNull(otpGenerator);
    }
}