package com.kafka.mart.auth.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class KafkaTopicConstantsTest {

    @Test
    void constants_shouldHaveExpectedValues() {

        assertEquals(
                "user-registered",
                KafkaTopicConstants.USER_REGISTERED
        );

        assertEquals(
                "user-verified",
                KafkaTopicConstants.USER_VERIFIED
        );

        assertEquals(
                "password-reset",
                KafkaTopicConstants.PASSWORD_RESET
        );
    }

    @Test
    void constructor_shouldBeCovered()
            throws Exception {

        Constructor<KafkaTopicConstants> constructor =
                KafkaTopicConstants.class
                        .getDeclaredConstructor();

        constructor.setAccessible(true);

        KafkaTopicConstants instance =
                constructor.newInstance();

        assertNotNull(instance);
    }
}