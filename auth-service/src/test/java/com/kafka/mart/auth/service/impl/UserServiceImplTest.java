package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByEmail_shouldReturnUser() {

        User user = new User();
        user.setEmail("test@gmail.com");

        when(
                userRepository.findByEmail(
                        "test@gmail.com"
                )
        ).thenReturn(
                Optional.of(user)
        );

        Optional<User> result =
                userService.findByEmail(
                        "test@gmail.com"
                );

        assertTrue(result.isPresent());

        assertEquals(
                "test@gmail.com",
                result.get().getEmail()
        );

        verify(userRepository)
                .findByEmail(
                        "test@gmail.com"
                );
    }

    @Test
    void findByPhone_shouldReturnUser() {

        User user = new User();
        user.setPhone("9999999999");

        when(
                userRepository.findByPhone(
                        "9999999999"
                )
        ).thenReturn(
                Optional.of(user)
        );

        Optional<User> result =
                userService.findByPhone(
                        "9999999999"
                );

        assertTrue(result.isPresent());

        assertEquals(
                "9999999999",
                result.get().getPhone()
        );

        verify(userRepository)
                .findByPhone(
                        "9999999999"
                );
    }

    @Test
    void save_shouldReturnSavedUser() {

        User user = new User();
        user.setEmail("save@gmail.com");

        when(
                userRepository.save(user)
        ).thenReturn(user);

        User result =
                userService.save(user);

        assertNotNull(result);

        assertEquals(
                "save@gmail.com",
                result.getEmail()
        );

        verify(userRepository)
                .save(user);
    }

    @Test
    void existsByEmail_shouldReturnTrue() {

        when(
                userRepository.existsByEmail(
                        "test@gmail.com"
                )
        ).thenReturn(true);

        boolean result =
                userService.existsByEmail(
                        "test@gmail.com"
                );

        assertTrue(result);

        verify(userRepository)
                .existsByEmail(
                        "test@gmail.com"
                );
    }

    @Test
    void existsByPhone_shouldReturnTrue() {

        when(
                userRepository.existsByPhone(
                        "9999999999"
                )
        ).thenReturn(true);

        boolean result =
                userService.existsByPhone(
                        "9999999999"
                );

        assertTrue(result);

        verify(userRepository)
                .existsByPhone(
                        "9999999999"
                );
    }
}