package com.kafka.mart.auth.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        log.warn(
                "Unauthorized access attempt : {}",
                request.getRequestURI()
        );

        response.setContentType(
                "application/json"
        );

        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED
        );

        log.warn(
                "Unauthorized access attempt : {}, reason : {}",
                request.getRequestURI(),
                authException.getMessage()
        );

        Map<String, Object> body =
                new HashMap<>();

        body.put(
                "success",
                false
        );

        body.put(
                "message",
                "Unauthorized"
        );

        body.put(
                "path",
                request.getServletPath()
        );

        new ObjectMapper()
                .writeValue(
                        response.getOutputStream(),
                        body
                );
    }
}