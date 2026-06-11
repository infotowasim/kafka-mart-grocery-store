package com.kafka.mart.auth.security.service;

import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username
    )
            throws UsernameNotFoundException {

        User user =
                userRepository.findByEmailOrPhone(
                                username,
                                username
                        )
                        .orElseThrow(() -> {

                            log.warn(
                                    "User not found : {}",
                                    username
                            );

                            return new UsernameNotFoundException(
                                    "User not found"
                            );
                        });

        return new CustomUserDetails(user);
    }
}