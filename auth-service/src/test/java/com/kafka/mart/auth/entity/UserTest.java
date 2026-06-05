package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void builderSuccess() {

        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();

        User user = User.builder()
                .id(1L)
                .firstName("Wasim")
                .lastName("Akram")
                .email("wasim@gmail.com")
                .phone("9999999999")
                .password("password")
                .role(role)
                .build();

        assertEquals(1L, user.getId());
        assertEquals("Wasim", user.getFirstName());
        assertEquals("Akram", user.getLastName());
        assertEquals("wasim@gmail.com", user.getEmail());
        assertEquals("9999999999", user.getPhone());
        assertEquals("password", user.getPassword());
        assertEquals(role, user.getRole());

        assertTrue(user.getEnabled());
        assertTrue(user.getAccountNonLocked());
        assertFalse(user.getEmailVerified());
        assertEquals(0, user.getFailedLoginAttempts());
    }

    @Test
    void getterSetterSuccess() {

        User user = new User();

        user.setId(10L);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@gmail.com");
        user.setPhone("1234567890");
        user.setPassword("pass");

        assertEquals(10L, user.getId());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
        assertEquals("pass", user.getPassword());
    }

    @Test
    void prePersistSuccess() {

        User user = new User();

        user.prePersist();

        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void preUpdateSuccess() {

        User user = new User();

        user.preUpdate();

        assertNotNull(user.getUpdatedAt());
    }
}