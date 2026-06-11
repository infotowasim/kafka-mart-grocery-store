package com.kafka.mart.auth.security.filter;

import com.kafka.mart.auth.security.service.CustomUserDetailsService;
import com.kafka.mart.auth.security.service.JwtService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setup() {

        SecurityContextHolder.clearContext();
    }


    @Test
    void shouldSkipWhenAuthorizationHeaderMissing()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(
                        request,
                        response
                );
    }


    @Test
    void shouldSkipWhenHeaderNotBearer()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Basic abc"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(
                        request,
                        response
                );
    }


    @Test
    void shouldAuthenticateUserWhenTokenValid()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer valid-token"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        UserDetails userDetails =
                mock(UserDetails.class);

        when(
                jwtService.extractUsername(
                        "valid-token"
                )
        ).thenReturn(
                "test@gmail.com"
        );

        when(
                userDetailsService
                        .loadUserByUsername(
                                "test@gmail.com"
                        )
        ).thenReturn(userDetails);

        when(
                jwtService.isTokenValid(
                        "valid-token",
                        userDetails
                )
        ).thenReturn(true);

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(
                        request,
                        response
                );
    }


    @Test
    void shouldNotAuthenticateWhenTokenInvalid()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer invalid-token"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        UserDetails userDetails =
                mock(UserDetails.class);

        when(
                jwtService.extractUsername(
                        "invalid-token"
                )
        ).thenReturn(
                "test@gmail.com"
        );

        when(
                userDetailsService
                        .loadUserByUsername(
                                "test@gmail.com"
                        )
        ).thenReturn(userDetails);

        when(
                jwtService.isTokenValid(
                        "invalid-token",
                        userDetails
                )
        ).thenReturn(false);

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(
                        request,
                        response
                );
    }


    @Test
    void shouldSkipAuthenticationWhenAlreadyAuthenticated()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer valid-token"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        mock(
                                org.springframework.security.core.Authentication.class
                        )
                );

        when(
                jwtService.extractUsername(
                        "valid-token"
                )
        ).thenReturn(
                "test@gmail.com"
        );

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(
                        request,
                        response
                );
    }



    @Test
    void shouldSkipWhenUsernameIsNull()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer token"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        when(
                jwtService.extractUsername(
                        "token"
                )
        ).thenReturn(null);

        jwtFilter.doFilter(
                request,
                response,
                filterChain
        );

        verify(filterChain)
                .doFilter(
                        request,
                        response
                );
    }



}