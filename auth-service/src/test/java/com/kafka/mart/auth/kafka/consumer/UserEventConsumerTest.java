package com.kafka.mart.auth.kafka.consumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserEventConsumerTest {

    private final UserEventConsumer
            consumer =
            new UserEventConsumer();

    @Test
    void consume_shouldProcessMessage() {

        assertDoesNotThrow(
                () -> consumer.consume(
                        "test-message"
                )
        );
    }
}