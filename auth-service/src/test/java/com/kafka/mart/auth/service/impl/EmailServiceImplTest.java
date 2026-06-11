package com.kafka.mart.auth.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void sendOtpEmail_shouldSendSuccessfully() {

        doNothing()
                .when(mailSender)
                .send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() ->
                emailService.sendOtpEmail(
                        "test@gmail.com",
                        "123456"
                )
        );

        verify(mailSender)
                .send(any(SimpleMailMessage.class));
    }

    @Test
    void sendOtpEmail_shouldHandleMailException() {

        doThrow(
                new MailSendException("Mail Error")
        )
                .when(mailSender)
                .send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() ->
                emailService.sendOtpEmail(
                        "test@gmail.com",
                        "123456"
                )
        );

        verify(mailSender)
                .send(any(SimpleMailMessage.class));
    }

    @Test
    void sendPasswordResetEmail_shouldSendSuccessfully() {

        doNothing()
                .when(mailSender)
                .send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() ->
                emailService.sendPasswordResetEmail(
                        "test@gmail.com",
                        "reset-token"
                )
        );

        verify(mailSender)
                .send(any(SimpleMailMessage.class));
    }

    @Test
    void sendPasswordResetEmail_shouldHandleMailException() {

        doThrow(
                new MailSendException("Mail Error")
        )
                .when(mailSender)
                .send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() ->
                emailService.sendPasswordResetEmail(
                        "test@gmail.com",
                        "reset-token"
                )
        );

        verify(mailSender)
                .send(any(SimpleMailMessage.class));
    }
}