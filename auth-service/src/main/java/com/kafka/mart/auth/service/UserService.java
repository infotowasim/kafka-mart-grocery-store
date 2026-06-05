package com.kafka.mart.auth.service;

import com.kafka.mart.auth.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}