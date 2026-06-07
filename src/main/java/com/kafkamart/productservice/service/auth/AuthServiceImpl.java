package com.kafkamart.productservice.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kafkamart.productservice.dto.request.LoginRequest;
import com.kafkamart.productservice.dto.request.RegisterRequest;
import com.kafkamart.productservice.dto.response.AuthResponse;
import com.kafkamart.productservice.entity.Role;
import com.kafkamart.productservice.entity.User;
import com.kafkamart.productservice.repository.UserRepository;
import com.kafkamart.productservice.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthServiceImpl(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			JwtService jwtService,
			AuthenticationManager authenticationManager) {

		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public AuthResponse register(RegisterRequest request) {

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already registered");
		}

		User user = new User();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.ROLE_CUSTOMER);
		userRepository.save(user);

		String token = jwtService.generateToken(user);

		return new AuthResponse(token);
	}

	@Override
	public AuthResponse login(LoginRequest request) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow();

		String token = jwtService.generateToken(user);

		return new AuthResponse(token);
	}
}
