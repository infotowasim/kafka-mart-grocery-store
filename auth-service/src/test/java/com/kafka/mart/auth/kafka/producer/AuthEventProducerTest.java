package com.kafka.mart.auth.kafka.producer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthEventProducerTest {

    @Mock
    private KafkaTemplate<String, Object>
            kafkaTemplate;

    @InjectMocks
    private AuthEventProducer
            authEventProducer;

    @Test
    void publish_shouldSendEventToKafka() {

        String topic =
                "user-registered";

        String event =
                "test-event";

        authEventProducer.publish(
                topic,
                event
        );

        verify(kafkaTemplate)
                .send(
                        topic,
                        event
                );
    }
}