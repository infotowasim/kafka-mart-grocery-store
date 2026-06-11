package com.kafka.mart.auth.security.service;

import com.kafka.mart.auth.entity.Role;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {

        Role role = Role.builder()
                .name("ROLE_CUSTOMER")
                .build();

        User user = User.builder()
                .email("test@gmail.com")
                .phone("9999999999")
                .password("password")
                .role(role)
                .accountNonLocked(true)
                .enabled(true)
                .build();

        when(
                userRepository.findByEmailOrPhone(
                        "test@gmail.com",
                        "test@gmail.com"
                )
        ).thenReturn(Optional.of(user));

        UserDetails result =
                customUserDetailsService
                        .loadUserByUsername(
                                "test@gmail.com"
                        );

        assertNotNull(result);

        assertEquals(
                "test@gmail.com",
                result.getUsername()
        );

        assertEquals(
                "password",
                result.getPassword()
        );

        assertTrue(
                result.isAccountNonLocked()
        );

        assertTrue(
                result.isEnabled()
        );

        verify(userRepository)
                .findByEmailOrPhone(
                        "test@gmail.com",
                        "test@gmail.com"
                );
    }

    @Test
    void loadUserByUsername_shouldThrowExceptionWhenUserNotFound() {

        when(
                userRepository.findByEmailOrPhone(
                        "unknown@gmail.com",
                        "unknown@gmail.com"
                )
        ).thenReturn(Optional.empty());

        UsernameNotFoundException exception =
                assertThrows(
                        UsernameNotFoundException.class,
                        () ->
                                customUserDetailsService
                                        .loadUserByUsername(
                                                "unknown@gmail.com"
                                        )
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );

        verify(userRepository)
                .findByEmailOrPhone(
                        "unknown@gmail.com",
                        "unknown@gmail.com"
                );
    }
}