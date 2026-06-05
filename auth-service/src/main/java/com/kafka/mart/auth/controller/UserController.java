package com.kafka.mart.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/v1/users/me")
    public Object currentUser(Authentication authentication) {

        return authentication.getPrincipal();
    }
}