package com.kafka.mart.auth.kafka.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVerifiedEvent {

    private Long userId;

    private String email;
}