package com.kafka.mart.auth.mapper;

import com.kafka.mart.auth.dto.request.RegisterRequest;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.entity.Role;
import com.kafka.mart.auth.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper =
            Mappers.getMapper(UserMapper.class);

    @Test
    void toEntitySuccess() {

        RegisterRequest request =
                new RegisterRequest();

        request.setFirstName("Wasim");
        request.setLastName("Akram");
        request.setEmail("wasim@gmail.com");
        request.setPhone("9999999999");
        request.setPassword("password");

        User user =
                userMapper.toEntity(request);

        assertNotNull(user);

        assertEquals(
                "Wasim",
                user.getFirstName()
        );

        assertEquals(
                "Akram",
                user.getLastName()
        );

        assertEquals(
                "wasim@gmail.com",
                user.getEmail()
        );

        assertEquals(
                "9999999999",
                user.getPhone()
        );

        // ignored fields
        assertNull(user.getId());
        assertNull(user.getRole());
        assertNull(user.getPassword());
    }

    @Test
    void toResponseSuccess() {

        Role role =
                Role.builder()
                        .id(1L)
                        .name("ROLE_ADMIN")
                        .build();

        User user =
                User.builder()
                        .id(1L)
                        .firstName("Wasim")
                        .lastName("Akram")
                        .email("wasim@gmail.com")
                        .phone("9999999999")
                        .role(role)
                        .build();

        UserResponse response =
                userMapper.toResponse(user);

        assertNotNull(response);

        assertEquals(
                1L,
                response.getId()
        );

        assertEquals(
                "Wasim",
                response.getFirstName()
        );

        assertEquals(
                "Akram",
                response.getLastName()
        );

        assertEquals(
                "wasim@gmail.com",
                response.getEmail()
        );

        assertEquals(
                "9999999999",
                response.getPhone()
        );

        assertEquals(
                "ROLE_ADMIN",
                response.getRole()
        );
    }
}