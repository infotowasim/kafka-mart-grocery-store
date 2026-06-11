package com.kafka.mart.auth.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    @Test
    void now_shouldReturnCurrentTime() {

        LocalDateTime result =
                DateTimeUtil.now();

        assertNotNull(result);
    }

    @Test
    void constructor_shouldBeCovered()
            throws Exception {

        Constructor<DateTimeUtil> constructor =
                DateTimeUtil.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        DateTimeUtil instance =
                constructor.newInstance();

        assertNotNull(instance);
    }
}