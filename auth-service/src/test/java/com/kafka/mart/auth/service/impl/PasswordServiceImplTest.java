package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.dto.request.ChangePasswordRequest;
import com.kafka.mart.auth.dto.request.ForgotPasswordRequest;
import com.kafka.mart.auth.dto.request.ResetPasswordByTokenRequest;
import com.kafka.mart.auth.entity.PasswordResetToken;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.exception.ResourceNotFoundException;
import com.kafka.mart.auth.repository.PasswordResetTokenRepository;
import com.kafka.mart.auth.repository.UserRepository;
import com.kafka.mart.auth.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordServiceImpl passwordService;

    private User user;

    @BeforeEach
    void setup() {

        user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("encoded-password")
                .build();
    }

    @Test
    void forgotPassword_shouldGenerateTokenSuccessfully() {

        ForgotPasswordRequest request =
                new ForgotPasswordRequest();

        request.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordResetTokenRepository.findByUser(user))
                .thenReturn(Optional.empty());

        passwordService.forgotPassword(request);

        verify(passwordResetTokenRepository)
                .save(any(PasswordResetToken.class));

        verify(emailService)
                .sendPasswordResetEmail(
                        eq("test@gmail.com"),
                        anyString()
                );
    }

    @Test
    void forgotPassword_shouldThrowExceptionWhenUserNotFound() {

        ForgotPasswordRequest request =
                new ForgotPasswordRequest();

        request.setEmail("unknown@gmail.com");

        when(userRepository.findByEmail("unknown@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> passwordService.forgotPassword(request)
        );
    }

    @Test
    void resetPasswordByToken_shouldResetPasswordSuccessfully() {

        ResetPasswordByTokenRequest request =
                new ResetPasswordByTokenRequest();

        request.setToken("valid-token");
        request.setNewPassword("new-password");

        PasswordResetToken token =
                PasswordResetToken.builder()
                        .token("valid-token")
                        .user(user)
                        .expiryDate(
                                LocalDateTime.now().plusMinutes(10)
                        )
                        .build();

        when(passwordResetTokenRepository.findByToken("valid-token"))
                .thenReturn(Optional.of(token));

        when(passwordEncoder.encode("new-password"))
                .thenReturn("encoded-new-password");

        passwordService.resetPasswordByToken(request);

        verify(userRepository)
                .save(user);

        verify(passwordResetTokenRepository)
                .delete(token);
    }

    @Test
    void resetPasswordByToken_shouldThrowExceptionWhenTokenInvalid() {

        ResetPasswordByTokenRequest request =
                new ResetPasswordByTokenRequest();

        request.setToken("invalid");

        when(passwordResetTokenRepository.findByToken("invalid"))
                .thenReturn(Optional.empty());

        assertThrows(
                BadRequestException.class,
                () -> passwordService.resetPasswordByToken(request)
        );
    }

    @Test
    void resetPasswordByToken_shouldThrowExceptionWhenTokenExpired() {

        ResetPasswordByTokenRequest request =
                new ResetPasswordByTokenRequest();

        request.setToken("expired-token");

        PasswordResetToken token =
                PasswordResetToken.builder()
                        .token("expired-token")
                        .user(user)
                        .expiryDate(
                                LocalDateTime.now().minusMinutes(1)
                        )
                        .build();

        when(passwordResetTokenRepository.findByToken("expired-token"))
                .thenReturn(Optional.of(token));

        assertThrows(
                BadRequestException.class,
                () -> passwordService.resetPasswordByToken(request)
        );
    }

    @Test
    void changePassword_shouldChangePasswordSuccessfully() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setOldPassword("old-password");
        request.setNewPassword("new-password");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        null
                )
        );

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "old-password",
                "encoded-password"
        )).thenReturn(true);

        when(passwordEncoder.encode("new-password"))
                .thenReturn("encoded-new-password");

        passwordService.changePassword(request);

        verify(userRepository)
                .save(user);
    }

    @Test
    void changePassword_shouldThrowExceptionWhenOldPasswordWrong() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setOldPassword("wrong-password");
        request.setNewPassword("new-password");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        null
                )
        );

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrong-password",
                "encoded-password"
        )).thenReturn(false);

        assertThrows(
                BadRequestException.class,
                () -> passwordService.changePassword(request)
        );
    }


    @Test
    void changePassword_shouldThrowExceptionWhenUserNotFound() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setOldPassword("old-password");
        request.setNewPassword("new-password");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "unknown@gmail.com",
                        null
                )
        );

        when(
                userRepository.findByEmail(
                        "unknown@gmail.com"
                )
        ).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> passwordService.changePassword(request)
        );
    }
}