package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void builder_shouldCreateRole() {

        Role role =
                Role.builder()
                        .id(1L)
                        .name("ROLE_ADMIN")
                        .build();

        assertEquals(
                1L,
                role.getId()
        );

        assertEquals(
                "ROLE_ADMIN",
                role.getName()
        );
    }

    @Test
    void settersAndGetters_shouldWork() {

        Role role =
                new Role();

        role.setId(2L);
        role.setName("ROLE_CUSTOMER");

        assertEquals(
                2L,
                role.getId()
        );

        assertEquals(
                "ROLE_CUSTOMER",
                role.getName()
        );
    }

    @Test
    void noArgsConstructor_shouldWork() {

        Role role =
                new Role();

        assertNotNull(role);
    }

    @Test
    void allArgsConstructor_shouldWork() {

        Role role =
                new Role(
                        1L,
                        "ROLE_DELIVERY"
                );

        assertEquals(
                1L,
                role.getId()
        );

        assertEquals(
                "ROLE_DELIVERY",
                role.getName()
        );
    }
}