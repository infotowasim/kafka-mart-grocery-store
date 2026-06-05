package com.kafka.mart.auth.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    private final UserController userController =
            new UserController();

    @Test
    void currentUserSuccess() {

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "test-user",
                        null
                );

        Object response =
                userController.currentUser(
                        authentication
                );

        assertEquals(
                "test-user",
                response
        );
    }
}