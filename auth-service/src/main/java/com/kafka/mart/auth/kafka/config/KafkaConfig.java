package com.kafka.mart.auth.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic userRegisteredTopic() {

        return new NewTopic(
                "user-registered",
                1,
                (short) 1
        );
    }

    @Bean
    public NewTopic userVerifiedTopic() {

        return new NewTopic(
                "user-verified",
                1,
                (short) 1
        );
    }

    @Bean
    public NewTopic passwordResetTopic() {

        return new NewTopic(
                "password-reset",
                1,
                (short) 1
        );
    }
}