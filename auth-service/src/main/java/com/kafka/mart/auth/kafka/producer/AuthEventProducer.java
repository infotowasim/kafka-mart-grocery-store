package com.kafka.mart.auth.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEventProducer {

    private final KafkaTemplate<String, Object>
            kafkaTemplate;

    public void publish(
            String topic,
            Object event
    ) {

        log.info(
                "Publishing event to topic : {}, event : {}",
                topic,
                event.getClass().getSimpleName()
        );

        kafkaTemplate.send(
                topic,
                event
        );
    }

}