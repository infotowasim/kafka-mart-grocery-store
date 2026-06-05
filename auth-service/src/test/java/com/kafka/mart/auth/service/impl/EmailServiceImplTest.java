package com.kafka.mart.auth.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailServiceImpl;

    @Test
    void sendOtpEmailSuccess() {

        emailServiceImpl.sendOtpEmail(
                "test@gmail.com",
                "123456"
        );

        verify(mailSender)
                .send(any(SimpleMailMessage.class));
    }
}