package com.kafkamart.productservice.service.auth;

import com.kafkamart.productservice.dto.request.LoginRequest;
import com.kafkamart.productservice.dto.request.RegisterRequest;
import com.kafkamart.productservice.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}