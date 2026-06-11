package com.kafka.mart.auth.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class OtpGeneratorTest {

    @Test
    void generateOtp_shouldReturnSixDigitOtp() {

        String otp = OtpGenerator.generateOtp();

        assertNotNull(otp);
        assertEquals(6, otp.length());
        assertTrue(otp.matches("\\d{6}"));
    }

    @Test
    void constructor_shouldBeCovered()
            throws Exception {

        Constructor<OtpGenerator> constructor =
                OtpGenerator.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        OtpGenerator instance =
                constructor.newInstance();

        assertNotNull(instance);
    }
}