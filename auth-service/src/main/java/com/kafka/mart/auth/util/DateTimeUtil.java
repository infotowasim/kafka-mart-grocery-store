package com.kafka.mart.auth.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class DateTimeUtil {

    private static final ZoneId INDIA_ZONE =
            ZoneId.of("Asia/Kolkata");

    DateTimeUtil() {
    }

    public static LocalDateTime now() {

        return LocalDateTime.now(
                INDIA_ZONE
        );
    }
}