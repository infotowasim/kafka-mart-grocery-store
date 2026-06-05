package com.kafka.mart.auth.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void builderSuccess() {

        Role role = Role.builder()
                .id(1L)
                .name("ROLE_ADMIN")
                .build();

        assertEquals(1L, role.getId());
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    void getterSetterSuccess() {

        Role role = new Role();

        role.setId(2L);
        role.setName("ROLE_USER");

        assertEquals(2L, role.getId());
        assertEquals("ROLE_USER", role.getName());
    }

    @Test
    void noArgsConstructorSuccess() {

        Role role = new Role();

        assertNotNull(role);
    }

    @Test
    void allArgsConstructorSuccess() {

        Role role =
                new Role(
                        1L,
                        "ROLE_ADMIN"
                );

        assertEquals(1L, role.getId());
        assertEquals("ROLE_ADMIN", role.getName());
    }
}