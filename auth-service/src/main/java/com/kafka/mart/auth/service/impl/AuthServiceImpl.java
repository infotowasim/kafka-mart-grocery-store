package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.constants.ErrorMessageConstants;
import com.kafka.mart.auth.constants.KafkaTopicConstants;
import com.kafka.mart.auth.dto.request.*;
import com.kafka.mart.auth.payload.ApiSuccessPayload;
import com.kafka.mart.auth.dto.response.LoginResponse;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.entity.*;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.exception.DuplicateResourceException;
import com.kafka.mart.auth.exception.ForbiddenException;
import com.kafka.mart.auth.exception.ResourceNotFoundException;
import com.kafka.mart.auth.kafka.producer.AuthEventProducer;
import com.kafka.mart.auth.mapper.EventMapper;
import com.kafka.mart.auth.mapper.UserMapper;
import com.kafka.mart.auth.repository.*;
import com.kafka.mart.auth.security.service.CustomUserDetailsService;
import com.kafka.mart.auth.security.service.JwtService;
import com.kafka.mart.auth.service.*;
import com.kafka.mart.auth.util.DateTimeUtil;
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

import com.kafka.mart.auth.entity.PasswordResetToken;
import com.kafka.mart.auth.repository.PasswordResetTokenRepository;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import static com.kafka.mart.auth.constants.ErrorMessageConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final OtpVerificationRepository otpVerificationRepository;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final AuthEventProducer authEventProducer;
    private final EventMapper eventMapper;
    private final AccountLockService accountLockService;
    private final OtpService otpService;
    private final EmailService emailService;





    @Override
    public ApiSuccessPayload register(RegisterRequest request) {


        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(ErrorMessageConstants.EMAIL_ALREADY_EXISTS);
        }


        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException(ErrorMessageConstants.PHONE_ALREADY_EXISTS);
        }


        Role role = roleRepository.findByName(RoleConstants.ROLE_CUSTOMER).orElseThrow(
                () -> new ResourceNotFoundException(
                                "Default role not found"
                        ));


        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(role);

        user.setLastOtpSentAt(DateTimeUtil.now());

        userRepository.save(user);

        String otp = otpService.generateOtp();

        otpService.saveOtp(user, otp);

        emailService.sendOtpEmail(user.getEmail(), otp);

        log.info("User registered and OTP sent successfully : {}", user.getEmail());

        authEventProducer.publish(KafkaTopicConstants.USER_REGISTERED, eventMapper.toUserRegisteredEvent(user));

        return ApiSuccessPayload.builder()
                .success(true)
                .message("Registration successful. Please verify your email.")
                .build();

    }





    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmailOrPhone(request.getUsername(), request.getUsername())
                        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.USER_NOT_FOUND));


        log.info("Login attempt : {}", request.getUsername());


        if (!user.isEmailVerified()) {
            throw new ForbiddenException("Please verify your email first");
        }

        if (!user.isAccountNonLocked()) {

            boolean unlocked = accountLockService.unlockWhenTimeExpired(user);

            if (!unlocked) {
                throw new ForbiddenException("Account is locked. Try again later.");
            }
        }

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        } catch (Exception ex) {

            accountLockService.increaseFailedAttempts(user);

            log.warn("Failed login attempt : {}", request.getUsername());


            User updatedUser = userRepository.findByEmail(user.getEmail()).orElse(user);

            if (!updatedUser.isAccountNonLocked()) {

                throw new ForbiddenException("Account locked after 5 failed attempts");
            }

            throw ex;
        }

        accountLockService.resetFailedAttempts(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        String accessToken = jwtService.generateToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        log.info("User logged in successfully : {}", user.getEmail());


        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(
                        refreshToken.getToken())
                .tokenType("Bearer")
                .user(
                        userMapper.toResponse(user))
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
                                        ErrorMessageConstants.USER_NOT_FOUND
                                ));

        return userMapper.toResponse(user);
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


        refreshTokenService
                .deleteRefreshToken(
                        request.getRefreshToken()
                );

        RefreshToken newRefreshToken =
                refreshTokenService
                        .createRefreshToken(
                                user
                        );



        log.info(
                "Access token refreshed for : {}",
                user.getEmail()
        );


        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(
                        newRefreshToken.getToken())
                .tokenType("Bearer")
                .user(
                        userMapper.toResponse(user)
                )
                .build();
    }


    @Override
    public ApiSuccessPayload logout(
            RefreshTokenRequest request
    ) {

        refreshTokenRepository.deleteByToken(
                request.getRefreshToken()
        );

        log.info(
                "User logged out successfully"
        );


        return ApiSuccessPayload.builder()
                .success(true)
                .message("Logged out successfully")
                .build();
    }







    @Override
    public ApiSuccessPayload verifyOtp(
            VerifyOtpRequest request
    ) {

        User user = userRepository.findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                ErrorMessageConstants.USER_NOT_FOUND
                        ));

        boolean valid =
                otpService.verifyOtp(
                        user,
                        request.getOtp()
                );

        if (!valid) {

            log.warn(
                    "Invalid OTP attempt : {}",
                    user.getEmail()
            );

            return ApiSuccessPayload.builder()
                    .success(false)
                    .message("Invalid or expired OTP")
                    .build();
        }

        user.setEmailVerified(true);

        userRepository.save(user);

        log.info(
                "OTP verified successfully : {}",
                user.getEmail()
        );


        authEventProducer.publish(
                KafkaTopicConstants.USER_VERIFIED,
                eventMapper.toUserVerifiedEvent(user)
        );

        return ApiSuccessPayload.builder()
                .success(true)
                .message("OTP verified successfully")
                .build();
    }


    @Override
    public ApiSuccessPayload resendOtp(
            OtpRequest request
    ) {

        User user = userRepository.findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                ErrorMessageConstants.USER_NOT_FOUND
                        ));

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


        log.info(
                "OTP resent successfully : {}",
                user.getEmail()
        );

        user.setLastOtpSentAt(
                DateTimeUtil.now()
        );

        userRepository.save(user);

        return ApiSuccessPayload.builder()
                .success(true)
                .message("OTP resent successfully")
                .build();
    }


}