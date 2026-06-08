package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.dto.request.ChangePasswordRequest;
import com.kafka.mart.auth.dto.request.ForgotPasswordRequest;
import com.kafka.mart.auth.dto.request.ResetPasswordByTokenRequest;
import com.kafka.mart.auth.dto.response.ApiResponse;
import com.kafka.mart.auth.entity.PasswordResetToken;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.BadRequestException;
import com.kafka.mart.auth.exception.ResourceNotFoundException;
import com.kafka.mart.auth.repository.PasswordResetTokenRepository;
import com.kafka.mart.auth.repository.UserRepository;
import com.kafka.mart.auth.service.EmailService;
import com.kafka.mart.auth.service.PasswordService;
import com.kafka.mart.auth.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kafka.mart.auth.constants.ErrorMessageConstants;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl
        implements PasswordService {

    private final UserRepository userRepository;
    private final EmailService emailService;



    private final PasswordResetTokenRepository
            passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse forgotPassword(
            ForgotPasswordRequest request
    ) {

        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        ErrorMessageConstants.USER_NOT_FOUND
                                ));

        PasswordResetToken token =
                passwordResetTokenRepository
                        .findByUser(user)
                        .orElse(
                                PasswordResetToken
                                        .builder()
                                        .user(user)
                                        .build()
                        );

        token.setToken(
                UUID.randomUUID().toString()
        );

        token.setExpiryDate(
                DateTimeUtil.now()
                        .plusMinutes(15)
        );

        passwordResetTokenRepository
                .save(token);

        emailService.sendPasswordResetEmail(
                user.getEmail(),
                token.getToken()
        );

        return ApiResponse.builder()
                .success(true)
                .message(
                        "Password reset token generated"
                )
                .build();
    }




    @Override
    public ApiResponse resetPasswordByToken(
            ResetPasswordByTokenRequest request
    ) {

        PasswordResetToken token =
                passwordResetTokenRepository
                        .findByToken(
                                request.getToken()
                        )
                        .orElseThrow(() ->
                                new BadRequestException(
                                        ErrorMessageConstants.INVALID_TOKEN
                                ));

        if (
                token.getExpiryDate()
                        .isBefore(
                                DateTimeUtil.now()
                        )
        ) {

            throw new BadRequestException(
                    ErrorMessageConstants.TOKEN_EXPIRED
            );
        }

        User user =
                token.getUser();

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        passwordResetTokenRepository
                .delete(token);

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

        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        ErrorMessageConstants.USER_NOT_FOUND
                                ));

        if (
                !passwordEncoder.matches(
                        request.getOldPassword(),
                        user.getPassword()
                )
        ) {

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
                .message(
                        "Password changed successfully"
                )
                .build();
    }
}