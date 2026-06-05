package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.dto.request.*;
import com.kafka.mart.auth.dto.response.ApiResponse;
import com.kafka.mart.auth.dto.response.LoginResponse;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.entity.OtpVerification;
import com.kafka.mart.auth.entity.RefreshToken;
import com.kafka.mart.auth.entity.Role;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.exception.DuplicateResourceException;
import com.kafka.mart.auth.exception.ForbiddenException;
import com.kafka.mart.auth.exception.ResourceNotFoundException;
import com.kafka.mart.auth.mapper.UserMapper;
import com.kafka.mart.auth.repository.OtpVerificationRepository;
import com.kafka.mart.auth.repository.RefreshTokenRepository;
import com.kafka.mart.auth.repository.RoleRepository;
import com.kafka.mart.auth.repository.UserRepository;
import com.kafka.mart.auth.security.CustomUserDetails;
import com.kafka.mart.auth.security.CustomUserDetailsService;
import com.kafka.mart.auth.security.JwtService;
import com.kafka.mart.auth.service.AuthService;
import com.kafka.mart.auth.service.EmailService;
import com.kafka.mart.auth.service.OtpService;
import com.kafka.mart.auth.service.RefreshTokenService;
import com.kafka.mart.auth.util.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final OtpService otpService;
    private final EmailService emailService;
    private final OtpVerificationRepository otpVerificationRepository;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public ApiResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException(
                    "Phone already exists"
            );
        }

        Role role = roleRepository.findByName(
                        RoleConstants.ROLE_CUSTOMER
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Default role not found"
                        ));

        User user = userMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(role);

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("Registration successful")
                .build();
    }

    @Override
    public ApiResponse sendOtp(OtpRequest request) {

        User user = userRepository.findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));
        if (
                user.getLastOtpSentAt() != null
                        &&
                        user.getLastOtpSentAt()
                                .plusSeconds(60)
                                .isAfter(LocalDateTime.now())
        ) {

            throw new BadRequestException(
                    "Please wait 60 seconds before requesting a new OTP"
            );
        }

        String otp = otpService.generateOtp();

        otpService.saveOtp(user, otp);

        emailService.sendOtpEmail(
                user.getEmail(),
                otp
        );

        user.setLastOtpSentAt(
                LocalDateTime.now()
        );

        userRepository.save(user);


        return ApiResponse.builder()
                .success(true)
                .message("OTP sent successfully")
                .build();
    }

    @Override
    public ApiResponse verifyOtp(
            VerifyOtpRequest request
    ) {

        User user = userRepository.findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        boolean valid = otpService.verifyOtp(
                user,
                request.getOtp()
        );

        if (!valid) {

            return ApiResponse.builder()
                    .success(false)
                    .message("Invalid or expired OTP")
                    .build();
        }

        user.setEmailVerified(true);

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("OTP verified successfully")
                .build();
    }

    @Override
    public ApiResponse forgotPassword(ForgotPasswordRequest request) {

        return ApiResponse.builder()
                .success(true)
                .message("Password reset link sent successfully")
                .build();
    }



    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        if (!user.getEmailVerified()) {

            throw new ForbiddenException(
                    "Please verify your email first"
            );
        }

        if (!user.getAccountNonLocked()) {

            throw new ForbiddenException(
                    "Account is locked"
            );
        }

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        } catch (Exception ex) {

            user.setFailedLoginAttempts(
                    user.getFailedLoginAttempts() + 1
            );

            System.out.println(
                    "FAILED LOGIN COUNT = "
                            + user.getFailedLoginAttempts()
            );

            if (user.getFailedLoginAttempts() >= 5) {

                user.setAccountNonLocked(false);

                userRepository.save(user);

                System.out.println(
                        "ACCOUNT LOCKED"
                );

                throw new ForbiddenException(
                        "Account locked after 5 failed attempts"
                );
            }

            userRepository.save(user);

            throw ex;
        }

        user.setFailedLoginAttempts(0);

        userRepository.save(user);

        UserDetails userDetails =
                customUserDetailsService
                        .loadUserByUsername(
                                user.getEmail()
                        );

        String token =
                jwtService.generateToken(
                        userDetails
                );

        RefreshToken refreshToken =
                refreshTokenService
                        .createRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(token)
                .refreshToken(
                        refreshToken.getToken()
                )
                .tokenType("Bearer")
                .user(
                        userMapper.toResponse(user)
                )
                .build();
    }


    @Override
    public ApiResponse resetPassword(
            ResetPasswordRequest request
    ) {

        User user = userRepository.findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        OtpVerification otpVerification =
                otpVerificationRepository
                        .findByUser(user)
                        .orElseThrow(() ->
                                new BadRequestException(
                                        "OTP not found"
                                ));

        if (!otpVerification.getVerified()) {
            throw new BadRequestException(
                    "OTP verification required"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        otpVerificationRepository.delete(
                otpVerification
        );

        return ApiResponse.builder()
                .success(true)
                .message(
                        "Password reset successfully"
                )
                .build();
    }


    @Override
    public ApiResponse changePassword(
            ChangePasswordRequest request
    ) {

        User user = userRepository.findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword()
        )) {
            throw new BadRequestException(
                    "Old password is incorrect"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("Password changed successfully")
                .build();
    }


    @Override
    public UserResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));

        return userMapper.toResponse(user);
    }


    @Override
    public ApiResponse resendOtp(
            OtpRequest request
    ) {

        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));

        if (
                user.getLastOtpSentAt() != null
                        &&
                        user.getLastOtpSentAt()
                                .plusSeconds(60)
                                .isAfter(LocalDateTime.now())
        ) {

            throw new BadRequestException(
                    "Please wait 60 seconds before requesting a new OTP"
            );
        }

        String otp =
                otpService.generateOtp();

        otpService.saveOtp(
                user,
                otp
        );

        emailService.sendOtpEmail(
                user.getEmail(),
                otp
        );

        user.setLastOtpSentAt(
                LocalDateTime.now()
        );

        userRepository.save(user);

        return ApiResponse.builder()
                .success(true)
                .message("OTP resent successfully")
                .build();
    }


    @Override
    public LoginResponse refreshToken(
            RefreshTokenRequest request
    ) {

        RefreshToken refreshToken =
                refreshTokenService
                        .verifyRefreshToken(
                                request.getRefreshToken()
                        );

        User user =
                refreshToken.getUser();

        UserDetails userDetails =
                customUserDetailsService
                        .loadUserByUsername(
                                user.getEmail()
                        );

        String accessToken =
                jwtService.generateToken(
                        userDetails
                );

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(
                        refreshToken.getToken()
                )
                .tokenType("Bearer")
                .user(
                        userMapper.toResponse(user)
                )
                .build();
    }


    @Override
    public ApiResponse logout(
            RefreshTokenRequest request
    ) {

        refreshTokenRepository.deleteByToken(
                request.getRefreshToken()
        );

        return ApiResponse.builder()
                .success(true)
                .message("Logged out successfully")
                .build();
    }


}