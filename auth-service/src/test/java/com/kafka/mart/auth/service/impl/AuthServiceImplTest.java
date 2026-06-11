package com.kafka.mart.auth.service.impl;

import com.kafka.mart.auth.constants.KafkaTopicConstants;
import com.kafka.mart.auth.dto.request.*;
import com.kafka.mart.auth.dto.response.LoginResponse;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.entity.RefreshToken;
import com.kafka.mart.auth.entity.Role;
import com.kafka.mart.auth.entity.User;
import com.kafka.mart.auth.exception.DuplicateResourceException;
import com.kafka.mart.auth.exception.ForbiddenException;
import com.kafka.mart.auth.exception.ResourceNotFoundException;
import com.kafka.mart.auth.kafka.producer.AuthEventProducer;
import com.kafka.mart.auth.mapper.EventMapper;
import com.kafka.mart.auth.mapper.UserMapper;
import com.kafka.mart.auth.payload.ApiSuccessPayload;
import com.kafka.mart.auth.repository.OtpVerificationRepository;
import com.kafka.mart.auth.repository.PasswordResetTokenRepository;
import com.kafka.mart.auth.repository.RefreshTokenRepository;
import com.kafka.mart.auth.repository.RoleRepository;
import com.kafka.mart.auth.repository.UserRepository;
import com.kafka.mart.auth.security.service.CustomUserDetailsService;
import com.kafka.mart.auth.security.service.JwtService;
import com.kafka.mart.auth.service.AccountLockService;
import com.kafka.mart.auth.service.EmailService;
import com.kafka.mart.auth.service.OtpService;
import com.kafka.mart.auth.service.RefreshTokenService;
import com.kafka.mart.auth.util.RoleConstants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private OtpVerificationRepository otpVerificationRepository;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private AuthEventProducer authEventProducer;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private AccountLockService accountLockService;

    @Mock
    private OtpService otpService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_shouldRegisterSuccessfully() {

        RegisterRequest request = new RegisterRequest();

        request.setFirstName("Wasim");
        request.setLastName("Akram");
        request.setEmail("test@gmail.com");
        request.setPhone("9999999999");
        request.setPassword("password");

        User user = User.builder()
                .email("test@gmail.com")
                .phone("9999999999")
                .build();

        Role role = Role.builder()
                .name("ROLE_CUSTOMER")
                .build();

        when(userRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByPhone(
                request.getPhone()))
                .thenReturn(false);

        when(roleRepository.findByName(
                RoleConstants.ROLE_CUSTOMER))
                .thenReturn(Optional.of(role));

        when(userMapper.toEntity(request))
                .thenReturn(user);

        when(passwordEncoder.encode("password"))
                .thenReturn("encoded-password");

        when(otpService.generateOtp())
                .thenReturn("123456");

        ApiSuccessPayload response =
                authService.register(request);

        assertTrue(response.isSuccess());

        verify(userRepository).save(user);

        verify(otpService)
                .saveOtp(user, "123456");

        verify(emailService)
                .sendOtpEmail(
                        "test@gmail.com",
                        "123456"
                );
    }

    @Test
    void register_shouldThrowWhenEmailAlreadyExists() {

        RegisterRequest request =
                new RegisterRequest();

        request.setEmail("test@gmail.com");

        when(userRepository.existsByEmail(
                "test@gmail.com"))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> authService.register(request)
        );
    }

    @Test
    void register_shouldThrowWhenPhoneAlreadyExists() {

        RegisterRequest request =
                new RegisterRequest();

        request.setEmail("test@gmail.com");
        request.setPhone("9999999999");

        when(userRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByPhone(
                request.getPhone()))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> authService.register(request)
        );
    }

    @Test
    void register_shouldThrowWhenRoleNotFound() {

        RegisterRequest request =
                new RegisterRequest();

        request.setEmail("test@gmail.com");
        request.setPhone("9999999999");

        when(userRepository.existsByEmail(
                request.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByPhone(
                request.getPhone()))
                .thenReturn(false);

        when(roleRepository.findByName(
                RoleConstants.ROLE_CUSTOMER))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.register(request)
        );
    }




    @Test
    void login_shouldThrowWhenUserNotFound() {

        LoginRequest request = new LoginRequest();

        request.setUsername("unknown@gmail.com");
        request.setPassword("password");

        when(
                userRepository.findByEmailOrPhone(
                        request.getUsername(),
                        request.getUsername()
                )
        ).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.login(request)
        );
    }




    @Test
    void login_shouldThrowWhenEmailNotVerified() {

        LoginRequest request = new LoginRequest();

        request.setUsername("test@gmail.com");
        request.setPassword("password");

        User user = User.builder()
                .email("test@gmail.com")
                .emailVerified(false)
                .build();

        when(
                userRepository.findByEmailOrPhone(
                        request.getUsername(),
                        request.getUsername()
                )
        ).thenReturn(Optional.of(user));

        assertThrows(
                ForbiddenException.class,
                () -> authService.login(request)
        );
    }



    @Test
    void login_shouldThrowWhenAccountLocked() {

        LoginRequest request = new LoginRequest();

        request.setUsername("test@gmail.com");
        request.setPassword("password");

        User user = User.builder()
                .email("test@gmail.com")
                .emailVerified(true)
                .accountNonLocked(false)
                .build();

        when(
                userRepository.findByEmailOrPhone(
                        request.getUsername(),
                        request.getUsername()
                )
        ).thenReturn(Optional.of(user));

        when(
                accountLockService.unlockWhenTimeExpired(user)
        ).thenReturn(false);

        assertThrows(
                ForbiddenException.class,
                () -> authService.login(request)
        );
    }



    @Test
    void login_shouldIncreaseFailedAttempts() {

        LoginRequest request = new LoginRequest();

        request.setUsername("test@gmail.com");
        request.setPassword("wrong");

        User user = User.builder()
                .email("test@gmail.com")
                .emailVerified(true)
                .accountNonLocked(true)
                .build();

        when(
                userRepository.findByEmailOrPhone(
                        request.getUsername(),
                        request.getUsername()
                )
        ).thenReturn(Optional.of(user));

        doThrow(new RuntimeException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any());

        when(
                userRepository.findByEmail(user.getEmail())
        ).thenReturn(Optional.of(user));

        assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );

        verify(accountLockService)
                .increaseFailedAttempts(user);
    }



    @Test
    void login_shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();

        request.setUsername("test@gmail.com");
        request.setPassword("password");

        User user = User.builder()
                .email("test@gmail.com")
                .emailVerified(true)
                .accountNonLocked(true)
                .build();

        UserDetails userDetails =
                mock(UserDetails.class);

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token("refresh-token")
                        .user(user)
                        .build();

        UserResponse userResponse =
                UserResponse.builder()
                        .email("test@gmail.com")
                        .build();

        when(
                userRepository.findByEmailOrPhone(
                        request.getUsername(),
                        request.getUsername()
                )
        ).thenReturn(Optional.of(user));

        when(
                customUserDetailsService
                        .loadUserByUsername(
                                user.getEmail()
                        )
        ).thenReturn(userDetails);

        when(
                jwtService.generateToken(
                        userDetails
                )
        ).thenReturn("access-token");

        when(
                refreshTokenService.createRefreshToken(user)
        ).thenReturn(refreshToken);

        when(
                userMapper.toResponse(user)
        ).thenReturn(userResponse);

        LoginResponse response =
                authService.login(request);

        assertNotNull(response);

        assertEquals(
                "access-token",
                response.getAccessToken()
        );

        assertEquals(
                "refresh-token",
                response.getRefreshToken()
        );

        verify(accountLockService)
                .resetFailedAttempts(user);
    }



    @Test
    void getCurrentUser_shouldReturnUser() {

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "test@gmail.com",
                        null
                )
        );

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        UserResponse response =
                UserResponse.builder()
                        .email("test@gmail.com")
                        .build();

        when(
                userRepository.findByEmail(
                        "test@gmail.com"
                )
        ).thenReturn(Optional.of(user));

        when(
                userMapper.toResponse(user)
        ).thenReturn(response);

        UserResponse result =
                authService.getCurrentUser();

        assertEquals(
                "test@gmail.com",
                result.getEmail()
        );
    }



    @Test
    void getCurrentUser_shouldThrowWhenUserNotFound() {

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
                () -> authService.getCurrentUser()
        );
    }



    @Test
    void refreshToken_shouldGenerateNewTokens() {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken(
                "old-refresh-token"
        );

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        RefreshToken oldToken =
                RefreshToken.builder()
                        .token("old-refresh-token")
                        .user(user)
                        .build();

        RefreshToken newToken =
                RefreshToken.builder()
                        .token("new-refresh-token")
                        .user(user)
                        .build();

        UserDetails userDetails =
                mock(UserDetails.class);

        UserResponse userResponse =
                UserResponse.builder()
                        .email("test@gmail.com")
                        .build();

        when(
                refreshTokenService.verifyRefreshToken(
                        "old-refresh-token"
                )
        ).thenReturn(oldToken);

        when(
                customUserDetailsService
                        .loadUserByUsername(
                                user.getEmail()
                        )
        ).thenReturn(userDetails);

        when(
                jwtService.generateToken(
                        userDetails
                )
        ).thenReturn("access-token");

        when(
                refreshTokenService
                        .createRefreshToken(user)
        ).thenReturn(newToken);

        when(
                userMapper.toResponse(user)
        ).thenReturn(userResponse);

        LoginResponse result =
                authService.refreshToken(request);

        assertEquals(
                "access-token",
                result.getAccessToken()
        );

        assertEquals(
                "new-refresh-token",
                result.getRefreshToken()
        );

        verify(refreshTokenService)
                .deleteRefreshToken(
                        "old-refresh-token"
                );
    }



    @Test
    void logout_shouldDeleteRefreshToken() {

        RefreshTokenRequest request =
                new RefreshTokenRequest();

        request.setRefreshToken(
                "refresh-token"
        );

        ApiSuccessPayload result =
                authService.logout(request);

        assertTrue(
                result.isSuccess()
        );

        verify(refreshTokenRepository)
                .deleteByToken(
                        "refresh-token"
                );
    }


    @Test
    void verifyOtp_shouldVerifySuccessfully() {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail(
                "test@gmail.com"
        );

        request.setOtp(
                "123456"
        );

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        when(
                userRepository.findByEmail(
                        "test@gmail.com"
                )
        ).thenReturn(Optional.of(user));

        when(
                otpService.verifyOtp(
                        user,
                        "123456"
                )
        ).thenReturn(true);

        ApiSuccessPayload result =
                authService.verifyOtp(request);

        assertTrue(
                result.isSuccess()
        );

        verify(userRepository)
                .save(user);

        verify(authEventProducer)
                .publish(
                        eq(KafkaTopicConstants.USER_VERIFIED),
                        any()
                );
    }


    @Test
    void verifyOtp_shouldReturnFalseWhenOtpInvalid() {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail(
                "test@gmail.com"
        );

        request.setOtp(
                "999999"
        );

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        when(
                userRepository.findByEmail(
                        "test@gmail.com"
                )
        ).thenReturn(Optional.of(user));

        when(
                otpService.verifyOtp(
                        user,
                        "999999"
                )
        ).thenReturn(false);

        ApiSuccessPayload result =
                authService.verifyOtp(request);

        assertFalse(
                result.isSuccess()
        );
    }


    @Test
    void resendOtp_shouldSendOtpSuccessfully() {

        OtpRequest request =
                new OtpRequest();

        request.setEmail(
                "test@gmail.com"
        );

        User user = User.builder()
                .email("test@gmail.com")
                .build();

        when(
                userRepository.findByEmail(
                        "test@gmail.com"
                )
        ).thenReturn(Optional.of(user));

        when(
                otpService.generateOtp()
        ).thenReturn("123456");

        ApiSuccessPayload result =
                authService.resendOtp(request);

        assertTrue(
                result.isSuccess()
        );

        verify(otpService)
                .saveOtp(
                        user,
                        "123456"
                );

        verify(emailService)
                .sendOtpEmail(
                        "test@gmail.com",
                        "123456"
                );
    }


    @Test
    void login_shouldThrowForbiddenWhenAccountLockedAfterFailedAttempts() {

        LoginRequest request = new LoginRequest();

        request.setUsername("test@gmail.com");
        request.setPassword("wrong");

        User user = User.builder()
                .email("test@gmail.com")
                .emailVerified(true)
                .accountNonLocked(true)
                .build();

        User lockedUser = User.builder()
                .email("test@gmail.com")
                .emailVerified(true)
                .accountNonLocked(false)
                .build();

        when(userRepository.findByEmailOrPhone(
                request.getUsername(),
                request.getUsername()))
                .thenReturn(Optional.of(user));

        doThrow(new RuntimeException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any());

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(lockedUser));

        assertThrows(
                ForbiddenException.class,
                () -> authService.login(request)
        );
    }


    @Test
    void verifyOtp_shouldThrowWhenUserNotFound() {

        VerifyOtpRequest request =
                new VerifyOtpRequest();

        request.setEmail(
                "unknown@gmail.com"
        );

        request.setOtp(
                "123456"
        );

        when(
                userRepository.findByEmail(
                        "unknown@gmail.com"
                )
        ).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.verifyOtp(request)
        );
    }


    @Test
    void resendOtp_shouldThrowWhenUserNotFound() {

        OtpRequest request =
                new OtpRequest();

        request.setEmail(
                "unknown@gmail.com"
        );

        when(
                userRepository.findByEmail(
                        "unknown@gmail.com"
                )
        ).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.resendOtp(request)
        );
    }


    @Test
    void login_shouldContinueWhenAccountUnlockedAfterLockDuration() {

        LoginRequest request =
                new LoginRequest();

        request.setUsername(
                "test@gmail.com"
        );

        request.setPassword(
                "password"
        );

        User user = User.builder()
                .email("test@gmail.com")
                .emailVerified(true)
                .accountNonLocked(false)
                .build();

        UserDetails userDetails =
                mock(UserDetails.class);

        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token("refresh-token")
                        .user(user)
                        .build();

        UserResponse userResponse =
                UserResponse.builder()
                        .email("test@gmail.com")
                        .build();

        when(
                userRepository.findByEmailOrPhone(
                        request.getUsername(),
                        request.getUsername()
                )
        ).thenReturn(Optional.of(user));

        when(
                accountLockService
                        .unlockWhenTimeExpired(user)
        ).thenReturn(true);

        when(
                customUserDetailsService
                        .loadUserByUsername(
                                user.getEmail()
                        )
        ).thenReturn(userDetails);

        when(
                jwtService.generateToken(
                        userDetails
                )
        ).thenReturn("access-token");

        when(
                refreshTokenService
                        .createRefreshToken(user)
        ).thenReturn(refreshToken);

        when(
                userMapper.toResponse(user)
        ).thenReturn(userResponse);

        LoginResponse response =
                authService.login(request);

        assertNotNull(response);
    }








}