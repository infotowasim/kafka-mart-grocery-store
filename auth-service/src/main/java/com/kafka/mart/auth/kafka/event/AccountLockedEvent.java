package com.kafka.mart.auth.kafka.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLockedEvent {

    private Long userId;

    private String email;
}