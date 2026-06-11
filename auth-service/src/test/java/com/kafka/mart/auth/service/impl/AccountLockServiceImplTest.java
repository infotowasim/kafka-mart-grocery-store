package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.repository.UserRepository;
import com.kafka.mart.auth.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountLockServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountLockServiceImpl accountLockService;

    @Test
    void increaseFailedAttempts_shouldIncreaseCounter() {

        User user = User.builder()
                .email("test@gmail.com")
                .failedLoginAttempts(2)
                .accountNonLocked(true)
                .build();

        accountLockService.increaseFailedAttempts(user);

        assertEquals(
                3,
                user.getFailedLoginAttempts()
        );

        assertTrue(
                user.isAccountNonLocked()
        );

        verify(userRepository)
                .save(user);
    }

    @Test
    void increaseFailedAttempts_shouldLockAccount() {

        User user = User.builder()
                .email("test@gmail.com")
                .failedLoginAttempts(
                        AppConstants.MAX_FAILED_ATTEMPTS - 1
                )
                .accountNonLocked(true)
                .build();

        accountLockService.increaseFailedAttempts(user);

        assertEquals(
                AppConstants.MAX_FAILED_ATTEMPTS,
                user.getFailedLoginAttempts()
        );

        assertFalse(
                user.isAccountNonLocked()
        );

        assertNotNull(
                user.getLockTime()
        );

        verify(userRepository)
                .save(user);
    }

    @Test
    void resetFailedAttempts_shouldResetCounter() {

        User user = User.builder()
                .failedLoginAttempts(4)
                .build();

        accountLockService.resetFailedAttempts(user);

        assertEquals(
                0,
                user.getFailedLoginAttempts()
        );

        verify(userRepository)
                .save(user);
    }

    @Test
    void unlockWhenTimeExpired_shouldUnlockAccount() {

        User user = User.builder()
                .email("test@gmail.com")
                .accountNonLocked(false)
                .failedLoginAttempts(5)
                .lockTime(
                        LocalDateTime.now()
                                .minusMinutes(
                                        AppConstants.LOCK_DURATION_MINUTES + 1
                                )
                )
                .build();

        boolean result =
                accountLockService.unlockWhenTimeExpired(user);

        assertTrue(result);

        assertTrue(
                user.isAccountNonLocked()
        );

        assertEquals(
                0,
                user.getFailedLoginAttempts()
        );

        assertNull(
                user.getLockTime()
        );

        verify(userRepository)
                .save(user);
    }

    @Test
    void unlockWhenTimeExpired_shouldNotUnlockAccount() {

        User user = User.builder()
                .email("test@gmail.com")
                .accountNonLocked(false)
                .failedLoginAttempts(5)
                .lockTime(
                        LocalDateTime.now()
                                .minusMinutes(5)
                )
                .build();

        boolean result =
                accountLockService.unlockWhenTimeExpired(user);

        assertFalse(result);

        verify(userRepository, never())
                .save(any());
    }

    @Test
    void unlockWhenTimeExpired_shouldReturnTrueWhenAlreadyUnlocked() {

        User user = User.builder()
                .accountNonLocked(true)
                .build();

        boolean result =
                accountLockService.unlockWhenTimeExpired(user);

        assertTrue(result);

        verify(userRepository, never())
                .save(any());
    }
}