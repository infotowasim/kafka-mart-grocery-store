package com.kafka.mart.auth.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventConsumer {

    @KafkaListener(
            topics = "user-registered",
            groupId = "auth-group"
    )
    public void consume(
            String message
    ) {

        log.info(
                "Received Event : {}",
                message
        );
    }
}