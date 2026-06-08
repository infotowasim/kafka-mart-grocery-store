package com.kafka.mart.auth.service;

import com.kafka.mart.auth.entity.User;

public interface AccountLockService {

    void increaseFailedAttempts(User user);

    void resetFailedAttempts(User user);

    boolean unlockWhenTimeExpired(User user);
}