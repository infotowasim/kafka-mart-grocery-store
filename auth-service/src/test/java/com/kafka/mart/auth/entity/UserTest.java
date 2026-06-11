package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void builder_shouldCreateUser() {

        Role role = new Role();
        role.setName("ROLE_ADMIN");

        User user =
                User.builder()
                        .id(1L)
                        .firstName("Wasim")
                        .lastName("Akram")
                        .email("test@gmail.com")
                        .phone("9999999999")
                        .password("password")
                        .role(role)
                        .enabled(true)
                        .accountNonLocked(true)
                        .emailVerified(false)
                        .failedLoginAttempts(0)
                        .build();

        assertEquals(
                1L,
                user.getId()
        );

        assertEquals(
                "Wasim",
                user.getFirstName()
        );

        assertEquals(
                "Akram",
                user.getLastName()
        );

        assertEquals(
                "test@gmail.com",
                user.getEmail()
        );

        assertEquals(
                "9999999999",
                user.getPhone()
        );

        assertEquals(
                role,
                user.getRole()
        );
    }

    @Test
    void prePersist_shouldSetCreatedAtAndUpdatedAt() {

        User user =
                new User();

        user.prePersist();

        assertNotNull(
                user.getCreatedAt()
        );

        assertNotNull(
                user.getUpdatedAt()
        );
    }

    @Test
    void preUpdate_shouldSetUpdatedAt() {

        User user =
                new User();

        user.preUpdate();

        assertNotNull(
                user.getUpdatedAt()
        );
    }

    @Test
    void settersAndGetters_shouldWork() {

        User user =
                new User();

        LocalDateTime now =
                LocalDateTime.now();

        user.setId(1L);
        user.setFirstName("Wasim");
        user.setLastName("Akram");
        user.setEmail("test@gmail.com");
        user.setPhone("9999999999");
        user.setPassword("password");
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setEmailVerified(true);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setLastOtpSentAt(now);
        user.setFailedLoginAttempts(5);
        user.setLockTime(now);

        assertEquals(1L, user.getId());
        assertEquals("Wasim", user.getFirstName());
        assertEquals("Akram", user.getLastName());
        assertEquals("test@gmail.com", user.getEmail());
        assertEquals("9999999999", user.getPhone());
        assertEquals("password", user.getPassword());
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isEmailVerified());
        assertEquals(5, user.getFailedLoginAttempts());
        assertEquals(now, user.getLockTime());
    }
}