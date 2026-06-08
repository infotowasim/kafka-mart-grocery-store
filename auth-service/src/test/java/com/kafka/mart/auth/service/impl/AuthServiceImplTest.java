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
import com.kafka.mart.auth.security.service.CustomUserDetails;
import com.kafka.mart.auth.security.service.CustomUserDetailsService;
import com.kafka.mart.auth.security.service.JwtService;
import com.kafka.mart.auth.service.EmailService;
import com.kafka.mart.auth.service.OtpService;
import com.kafka.mart.auth.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private OtpService otpService;

    @Mock
    private EmailService emailService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private OtpVerificationRepository otpVerificationRepository;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;


    @Test
    void logoutSuccess() {

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("abc123");

        ApiResponse response =
                authServiceImpl.logout(request);

        verify(refreshTokenRepository)
                .deleteByToken("abc123");

        assertTrue(response.isSuccess());

        assertEquals(
                "Logged out successfully",
                response.getMessage()
        );
    }



    @Test
    void registerSuccess() {

        RegisterRequest request = new RegisterRequest();

        request.setEmail("test@gmail.com");
        request.setPhone("9999999999");
        request.setPassword("123456");

        User user = new User();
        Role role = new Role();

        when(userRepository.existsByEmail(
                request.getEmail()
        )).thenReturn(false);

        when(userRepository.existsByPhone(
                request.getPhone()
        )).thenReturn(false);

        when(roleRepository.findByName(anyString()))
                .thenReturn(Optional.of(role));

        when(userMapper.toEntity(request))
                .thenReturn(user);

        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        ApiResponse response =
                authServiceImpl.register(request);

        assertTrue(response.isSuccess());

        assertEquals(
                "Registration successful",
                response.getMessage()
        );

        verify(userRepository).save(user);
    }



    @Test
    void registerEmailAlreadyExists() {

        RegisterRequest request = new RegisterRequest();

        request.setEmail("test@gmail.com");

        when(userRepository.existsByEmail(
                request.getEmail()
        )).thenReturn(true);

        DuplicateResourceException exception =
                assertThrows(
                        DuplicateResourceException.class,
                        () -> authServiceImpl.register(request)
                );

        assertEquals(
                "Email already exists",
                exception.getMessage()
        );

        verify(userRepository, never())
                .save(any(User.class));
    }



    @Test
    void registerPhoneAlreadyExists() {

        RegisterRequest request = new RegisterRequest();

        request.setEmail("test@gmail.com");
        request.setPhone("9999999999");

        when(userRepository.existsByEmail(
                request.getEmail()
        )).thenReturn(false);

        when(userRepository.existsByPhone(
                request.getPhone()
        )).thenReturn(true);

        DuplicateResourceException exception =
                assertThrows(
                        DuplicateResourceException.class,
                        () -> authServiceImpl.register(request)
                );

        assertEquals(
                "Phone already exists",
                exception.getMessage()
        );
    }



    @Test
    void registerRoleNotFound() {

        RegisterRequest request = new RegisterRequest();

        request.setEmail("test@gmail.com");
        request.setPhone("9999999999");

        when(userRepository.existsByEmail(
                request.getEmail()
        )).thenReturn(false);

        when(userRepository.existsByPhone(
                request.getPhone()
        )).thenReturn(false);

        when(roleRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> authServiceImpl.register(request)
                );

        assertEquals(
                "Default role not found",
                exception.getMessage()
        );
    }



    @Test
    void loginUserNotFound() {

        LoginRequest request = new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("123456");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> authServiceImpl.login(request)
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }



    @Test
    void loginEmailNotVerified() {

        LoginRequest request = new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("123456");

        User user = new User();

        user.setEmailVerified(false);

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        ForbiddenException exception =
                assertThrows(
                        ForbiddenException.class,
                        () -> authServiceImpl.login(request)
                );

        assertEquals(
                "Please verify your email first",
                exception.getMessage()
        );
    }



    @Test
    void loginAccountLocked() {

        LoginRequest request = new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("123456");

        User user = new User();

        user.setEmailVerified(true);
        user.setAccountNonLocked(false);

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        ForbiddenException exception =
                assertThrows(
                        ForbiddenException.class,
                        () -> authServiceImpl.login(request)
                );

        assertEquals(
                "Account is locked",
                exception.getMessage()
        );
    }



    @Test
    void loginSuccess() {

        LoginRequest request = new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("123456");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setEmailVerified(true);
        user.setAccountNonLocked(true);

        UserResponse userResponse =
                UserResponse.builder()
                        .id(1L)
                        .firstName("Test")
                        .lastName("User")
                        .email("test@gmail.com")
                        .phone("9999999999")
                        .role("CUSTOMER")
                        .build();

        CustomUserDetails userDetails =
                mock(CustomUserDetails.class);

        RefreshToken refreshToken =
                new RefreshToken();

        refreshToken.setToken("refresh-token");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(customUserDetailsService
                .loadUserByUsername(
                        user.getEmail()
                )).thenReturn(userDetails);

        when(jwtService.generateToken(
                userDetails
        )).thenReturn("jwt-token");

        when(refreshTokenService
                .createRefreshToken(user))
                .thenReturn(refreshToken);

        when(userMapper.toResponse(user))
                .thenReturn(userResponse);

        LoginResponse response =
                authServiceImpl.login(request);

        assertNotNull(response);

        assertEquals(
                "jwt-token",
                response.getAccessToken()
        );

        assertEquals(
                "refresh-token",
                response.getRefreshToken()
        );

        verify(authenticationManager)
                .authenticate(any());
    }



    @Test
    void loginInvalidPassword() {

        LoginRequest request = new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("wrong-password");

        User user = new User();

        user.setEmailVerified(true);
        user.setAccountNonLocked(true);
        user.setFailedLoginAttempts(0);

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        doThrow(
                new RuntimeException("Bad credentials")
        )
                .when(authenticationManager)
                .authenticate(any());

        assertThrows(
                RuntimeException.class,
                () -> authServiceImpl.login(request)
        );

        verify(userRepository)
                .save(user);
    }



    @Test
    void loginLockAccountAfter5Attempts() {

        LoginRequest request = new LoginRequest();

        request.setEmail("test@gmail.com");
        request.setPassword("wrong-password");

        User user = new User();

        user.setEmailVerified(true);
        user.setAccountNonLocked(true);
        user.setFailedLoginAttempts(4);

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        doThrow(
                new RuntimeException("Bad credentials")
        )
                .when(authenticationManager)
                .authenticate(any());

        ForbiddenException exception =
                assertThrows(
                        ForbiddenException.class,
                        () -> authServiceImpl.login(request)
                );

        assertEquals(
                "Account locked after 5 failed attempts",
                exception.getMessage()
        );

        assertFalse(
                user.getAccountNonLocked()
        );

        verify(userRepository)
                .save(user);
    }



    @Test
    void sendOtpSuccess() {

        OtpRequest request = new OtpRequest();
        request.setEmail("test@gmail.com");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setLastOtpSentAt(null);

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpService.generateOtp())
                .thenReturn("123456");

        ApiResponse response =
                authServiceImpl.sendOtp(request);

        assertTrue(response.isSuccess());

        assertEquals(
                "OTP sent successfully",
                response.getMessage()
        );

        verify(emailService)
                .sendOtpEmail(
                        eq("test@gmail.com"),
                        eq("123456")
                );

        verify(userRepository)
                .save(user);
    }



    @Test
    void sendOtpUserNotFound() {

        OtpRequest request = new OtpRequest();
        request.setEmail("test@gmail.com");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> authServiceImpl.sendOtp(request)
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }



    @Test
    void sendOtpBefore60Seconds() {

        OtpRequest request = new OtpRequest();
        request.setEmail("test@gmail.com");

        User user = new User();

        user.setLastOtpSentAt(
                LocalDateTime.now()
                        .minusSeconds(30)
        );

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> authServiceImpl.sendOtp(request)
                );

        assertEquals(
                "Please wait 60 seconds before requesting a new OTP",
                exception.getMessage()
        );
    }




    @Test
    void sendOtpAfter60Seconds() {

        OtpRequest request = new OtpRequest();
        request.setEmail("test@gmail.com");

        User user = new User();
        user.setEmail("test@gmail.com");

        user.setLastOtpSentAt(
                LocalDateTime.now()
                        .minusSeconds(120)
        );

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpService.generateOtp())
                .thenReturn("123456");

        ApiResponse response =
                authServiceImpl.sendOtp(request);

        assertTrue(response.isSuccess());

        verify(userRepository)
                .save(user);
    }




    @Test
    void verifyOtpSuccess() {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail("test@gmail.com");
        request.setOtp("123456");

        User user = new User();

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpService.verifyOtp(
                user,
                "123456"
        )).thenReturn(true);

        ApiResponse response =
                authServiceImpl.verifyOtp(request);

        assertTrue(response.isSuccess());

        assertEquals(
                "OTP verified successfully",
                response.getMessage()
        );

        verify(userRepository)
                .save(user);
    }



    @Test
    void verifyOtpInvalid() {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail("test@gmail.com");
        request.setOtp("999999");

        User user = new User();

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpService.verifyOtp(
                user,
                "999999"
        )).thenReturn(false);

        ApiResponse response =
                authServiceImpl.verifyOtp(request);

        assertFalse(response.isSuccess());

        assertEquals(
                "Invalid or expired OTP",
                response.getMessage()
        );
    }



    @Test
    void verifyOtpUserNotFound() {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail("test@gmail.com");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> authServiceImpl.verifyOtp(request)
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }

    @Test
    void resendOtpAfter60Seconds() {

        OtpRequest request = new OtpRequest();
        request.setEmail("test@gmail.com");

        User user = new User();
        user.setEmail("test@gmail.com");

        user.setLastOtpSentAt(
                LocalDateTime.now()
                        .minusSeconds(120)
        );

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpService.generateOtp())
                .thenReturn("123456");

        ApiResponse response =
                authServiceImpl.resendOtp(request);

        assertTrue(response.isSuccess());

        verify(userRepository)
                .save(user);
    }



    @Test
    void refreshTokenSuccess() {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken("refresh-token");

        User user = new User();
        user.setEmail("test@gmail.com");

        RefreshToken refreshToken =
                new RefreshToken();

        refreshToken.setToken("refresh-token");
        refreshToken.setUser(user);

        UserResponse userResponse =
                UserResponse.builder()
                        .id(1L)
                        .firstName("Test")
                        .lastName("User")
                        .email("test@gmail.com")
                        .phone("9999999999")
                        .role("CUSTOMER")
                        .build();

        CustomUserDetails userDetails =
                mock(CustomUserDetails.class);

        when(refreshTokenService
                .verifyRefreshToken(
                        "refresh-token"
                ))
                .thenReturn(refreshToken);

        when(customUserDetailsService
                .loadUserByUsername(
                        "test@gmail.com"
                ))
                .thenReturn(userDetails);

        when(jwtService.generateToken(
                userDetails
        )).thenReturn("new-access-token");

        when(userMapper.toResponse(user))
                .thenReturn(userResponse);

        LoginResponse response =
                authServiceImpl.refreshToken(request);

        assertNotNull(response);

        assertEquals(
                "new-access-token",
                response.getAccessToken()
        );

        assertEquals(
                "refresh-token",
                response.getRefreshToken()
        );
    }



    @Test
    void changePasswordSuccess() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setEmail("test@gmail.com");
        request.setOldPassword("old123");
        request.setNewPassword("new123");

        User user = new User();

        user.setPassword("encodedOld");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "old123",
                "encodedOld"
        )).thenReturn(true);

        when(passwordEncoder.encode(
                "new123"
        )).thenReturn("encodedNew");

        ApiResponse response =
                authServiceImpl.changePassword(request);

        assertTrue(response.isSuccess());

        assertEquals(
                "Password changed successfully",
                response.getMessage()
        );

        verify(userRepository)
                .save(user);
    }



    @Test
    void changePasswordUserNotFound() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setEmail("test@gmail.com");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> authServiceImpl.changePassword(request)
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }



    @Test
    void changePasswordOldPasswordIncorrect() {

        ChangePasswordRequest request =
                new ChangePasswordRequest();

        request.setEmail("test@gmail.com");
        request.setOldPassword("wrong");
        request.setNewPassword("new123");

        User user = new User();

        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrong",
                "encodedPassword"
        )).thenReturn(false);

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> authServiceImpl.changePassword(request)
                );

        assertEquals(
                "Old password is incorrect",
                exception.getMessage()
        );
    }



    @Test
    void resetPasswordSuccess() {

        ResetPasswordRequest request =
                new ResetPasswordRequest();

        request.setEmail("test@gmail.com");
        request.setNewPassword("new123");

        User user = new User();

        OtpVerification otpVerification =
                new OtpVerification();

        otpVerification.setVerified(true);

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpVerificationRepository.findByUser(
                user
        )).thenReturn(Optional.of(otpVerification));

        when(passwordEncoder.encode(
                "new123"
        )).thenReturn("encodedPassword");

        ApiResponse response =
                authServiceImpl.resetPassword(request);

        assertTrue(response.isSuccess());

        assertEquals(
                "Password reset successfully",
                response.getMessage()
        );

        verify(userRepository)
                .save(user);

        verify(otpVerificationRepository)
                .delete(otpVerification);
    }



    @Test
    void resetPasswordUserNotFound() {

        ResetPasswordRequest request =
                new ResetPasswordRequest();

        request.setEmail("test@gmail.com");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> authServiceImpl.resetPassword(request)
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }



    @Test
    void resetPasswordOtpNotFound() {

        ResetPasswordRequest request =
                new ResetPasswordRequest();

        request.setEmail("test@gmail.com");

        User user = new User();

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpVerificationRepository.findByUser(
                user
        )).thenReturn(Optional.empty());

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> authServiceImpl.resetPassword(request)
                );

        assertEquals(
                "OTP not found",
                exception.getMessage()
        );
    }



    @Test
    void resetPasswordOtpNotVerified() {

        ResetPasswordRequest request =
                new ResetPasswordRequest();

        request.setEmail("test@gmail.com");

        User user = new User();

        OtpVerification otpVerification =
                new OtpVerification();

        otpVerification.setVerified(false);

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpVerificationRepository.findByUser(
                user
        )).thenReturn(Optional.of(otpVerification));

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> authServiceImpl.resetPassword(request)
                );

        assertEquals(
                "OTP verification required",
                exception.getMessage()
        );
    }



    @Test
    void getCurrentUserSuccess() {

        User user = new User();
        user.setEmail("test@gmail.com");

        UserResponse userResponse =
                UserResponse.builder()
                        .id(1L)
                        .email("test@gmail.com")
                        .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        null
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(userRepository.findByEmail(
                "test@gmail.com"
        )).thenReturn(Optional.of(user));

        when(userMapper.toResponse(user))
                .thenReturn(userResponse);

        UserResponse response =
                authServiceImpl.getCurrentUser();

        assertNotNull(response);

        assertEquals(
                "test@gmail.com",
                response.getEmail()
        );
    }



    @Test
    void getCurrentUserNotFound() {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        null
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(userRepository.findByEmail(
                "test@gmail.com"
        )).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> authServiceImpl.getCurrentUser()
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }



    @Test
    void resendOtpSuccess() {

        OtpRequest request =
                new OtpRequest();

        request.setEmail("test@gmail.com");

        User user = new User();

        user.setEmail("test@gmail.com");
        user.setLastOtpSentAt(null);

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        when(otpService.generateOtp())
                .thenReturn("123456");

        ApiResponse response =
                authServiceImpl.resendOtp(request);

        assertTrue(response.isSuccess());

        assertEquals(
                "OTP resent successfully",
                response.getMessage()
        );

        verify(emailService)
                .sendOtpEmail(
                        eq("test@gmail.com"),
                        eq("123456")
                );
    }



    @Test
    void resendOtpUserNotFound() {

        OtpRequest request =
                new OtpRequest();

        request.setEmail("test@gmail.com");

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> authServiceImpl.resendOtp(request)
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }



    @Test
    void resendOtpBefore60Seconds() {

        OtpRequest request =
                new OtpRequest();

        request.setEmail("test@gmail.com");

        User user = new User();

        user.setLastOtpSentAt(
                LocalDateTime.now()
                        .minusSeconds(30)
        );

        when(userRepository.findByEmail(
                request.getEmail()
        )).thenReturn(Optional.of(user));

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> authServiceImpl.resendOtp(request)
                );

        assertEquals(
                "Please wait 60 seconds before requesting a new OTP",
                exception.getMessage()
        );
    }



    @Test
    void forgotPasswordSuccess() {

        ForgotPasswordRequest request =
                new ForgotPasswordRequest();

        request.setEmail("test@gmail.com");

        ApiResponse response =
                authServiceImpl.forgotPassword(request);

        assertTrue(response.isSuccess());

        assertEquals(
                "Password reset link sent successfully",
                response.getMessage()
        );
    }



}