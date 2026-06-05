package com.kafka.mart.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    @Test
    void constructorSuccess() {

        DateTimeUtil dateTimeUtil =
                new DateTimeUtil();

        assertNotNull(dateTimeUtil);
    }
}