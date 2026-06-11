package com.kafka.mart.auth.security.entrypoint;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;



class JwtAuthenticationEntryPointTest {

    private final JwtAuthenticationEntryPoint entryPoint =
            new JwtAuthenticationEntryPoint();



    @Test
    void commence_shouldReturnUnauthorizedResponse()
            throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.setRequestURI(
                "/api/v1/users/me"
        );

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        BadCredentialsException exception =
                new BadCredentialsException(
                        "Invalid token"
                );

        entryPoint.commence(
                request,
                response,
                exception
        );

        assertEquals(
                401,
                response.getStatus()
        );

        assertEquals(
                "application/json",
                response.getContentType()
        );

        String content =
                response.getContentAsString();

        assertTrue(
                content.contains("Unauthorized")
        );

        assertTrue(
                content.contains("success")
        );
    }



}


