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
    private UserServiceImpl userServiceImpl;

    @Test
    void findByEmailSuccess() {

        User user = new User();
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail(
                "test@gmail.com"
        )).thenReturn(Optional.of(user));

        Optional<User> result =
                userServiceImpl.findByEmail(
                        "test@gmail.com"
                );

        assertTrue(result.isPresent());

        assertEquals(
                "test@gmail.com",
                result.get().getEmail()
        );
    }

    @Test
    void findByPhoneSuccess() {

        User user = new User();
        user.setPhone("9999999999");

        when(userRepository.findByPhone(
                "9999999999"
        )).thenReturn(Optional.of(user));

        Optional<User> result =
                userServiceImpl.findByPhone(
                        "9999999999"
                );

        assertTrue(result.isPresent());

        assertEquals(
                "9999999999",
                result.get().getPhone()
        );
    }

    @Test
    void saveSuccess() {

        User user = new User();

        when(userRepository.save(user))
                .thenReturn(user);

        User result =
                userServiceImpl.save(user);

        assertNotNull(result);

        verify(userRepository)
                .save(user);
    }

    @Test
    void existsByEmailTrue() {

        when(userRepository.existsByEmail(
                "test@gmail.com"
        )).thenReturn(true);

        boolean result =
                userServiceImpl.existsByEmail(
                        "test@gmail.com"
                );

        assertTrue(result);
    }

    @Test
    void existsByEmailFalse() {

        when(userRepository.existsByEmail(
                "test@gmail.com"
        )).thenReturn(false);

        boolean result =
                userServiceImpl.existsByEmail(
                        "test@gmail.com"
                );

        assertFalse(result);
    }

    @Test
    void existsByPhoneTrue() {

        when(userRepository.existsByPhone(
                "9999999999"
        )).thenReturn(true);

        boolean result =
                userServiceImpl.existsByPhone(
                        "9999999999"
                );

        assertTrue(result);
    }

    @Test
    void existsByPhoneFalse() {

        when(userRepository.existsByPhone(
                "9999999999"
        )).thenReturn(false);

        boolean result =
                userServiceImpl.existsByPhone(
                        "9999999999"
                );

        assertFalse(result);
    }
}