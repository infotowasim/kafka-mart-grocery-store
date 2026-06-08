package com.kafka.mart.auth.kafka.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent {

    private Long userId;

    private String email;

    private String firstName;

    private String lastName;
}