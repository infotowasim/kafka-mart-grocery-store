package com.kafka.mart.auth.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class AppConstantsTest {

    @Test
    void constants_shouldHaveExpectedValues() {

        assertEquals(
                5,
                AppConstants.MAX_FAILED_ATTEMPTS
        );

        assertEquals(
                30L,
                AppConstants.LOCK_DURATION_MINUTES
        );
    }

    @Test
    void constructor_shouldBeCovered()
            throws Exception {

        Constructor<AppConstants> constructor =
                AppConstants.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        AppConstants instance =
                constructor.newInstance();

        assertNotNull(instance);
    }
}