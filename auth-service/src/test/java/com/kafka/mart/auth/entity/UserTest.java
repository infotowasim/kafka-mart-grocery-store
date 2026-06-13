package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final LocalDateTime TEST_TIME =
            LocalDateTime.of(
                    2025,
                    1,
                    1,
                    10,
                    0
            );

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
                "password",
                user.getPassword()
        );

        assertEquals(
                role,
                user.getRole()
        );

        assertTrue(
                user.isEnabled()
        );

        assertTrue(
                user.isAccountNonLocked()
        );

        assertFalse(
                user.isEmailVerified()
        );

        assertEquals(
                0,
                user.getFailedLoginAttempts()
        );
    }

    @Test
    void settersAndGetters_shouldWork() {

        User user =
                new User();

        Role role =
                new Role();

        user.setId(1L);
        user.setFirstName("Wasim");
        user.setLastName("Akram");
        user.setEmail("test@gmail.com");
        user.setPhone("9999999999");
        user.setPassword("password");
        user.setRole(role);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setEmailVerified(true);
        user.setLastOtpSentAt(TEST_TIME);
        user.setFailedLoginAttempts(5);
        user.setLockTime(TEST_TIME);

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
                "password",
                user.getPassword()
        );

        assertEquals(
                role,
                user.getRole()
        );

        assertTrue(
                user.isEnabled()
        );

        assertTrue(
                user.isAccountNonLocked()
        );

        assertTrue(
                user.isEmailVerified()
        );

        assertEquals(
                TEST_TIME,
                user.getLastOtpSentAt()
        );

        assertEquals(
                5,
                user.getFailedLoginAttempts()
        );

        assertEquals(
                TEST_TIME,
                user.getLockTime()
        );
    }

    @Test
    void noArgsConstructor_shouldWork() {

        User user =
                new User();

        assertNotNull(
                user
        );
    }

    @Test
    void builderDefaultValues_shouldBeApplied() {

        User user =
                User.builder()
                        .build();

        assertTrue(
                user.isEnabled()
        );

        assertTrue(
                user.isAccountNonLocked()
        );

        assertFalse(
                user.isEmailVerified()
        );

        assertEquals(
                0,
                user.getFailedLoginAttempts()
        );
    }
}