package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.repository.UserRepository;
import com.kafka.mart.auth.service.AccountLockService;
import com.kafka.mart.auth.util.AppConstants;
import com.kafka.mart.auth.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountLockServiceImpl
        implements AccountLockService {

    private final UserRepository userRepository;

    @Override
    public void increaseFailedAttempts(
            User user
    ) {

        int attempts =
                user.getFailedLoginAttempts() + 1;

        user.setFailedLoginAttempts(
                attempts
        );

        if (
                attempts >=
                        AppConstants.MAX_FAILED_ATTEMPTS
        ) {

            user.setAccountNonLocked(false);

            user.setLockTime(
                    DateTimeUtil.now()
            );

            log.warn(
                    "Account locked for user : {}",
                    user.getEmail()
            );
        }

        log.warn(
                "Failed login attempt {} for user : {}",
                attempts,
                user.getEmail()
        );


        userRepository.save(user);
    }



    @Override
    public void resetFailedAttempts(
            User user
    ) {

        user.setFailedLoginAttempts(0);

        userRepository.save(user);
    }

    @Override
    public boolean unlockWhenTimeExpired(
            User user
    ) {

        if (
                user.isAccountNonLocked()
        ) {
            return true;
        }

        LocalDateTime unlockTime =
                user.getLockTime()
                        .plusMinutes(
                                AppConstants
                                        .LOCK_DURATION_MINUTES
                        );

        if (
                unlockTime.isBefore(
                        DateTimeUtil.now()
                )
        ) {

            user.setAccountNonLocked(true);

            user.setFailedLoginAttempts(0);

            user.setLockTime(null);


            log.info(
                    "Account unlocked for user : {}",
                    user.getEmail()
            );

            userRepository.save(user);

            return true;
        }

        return false;
    }
}