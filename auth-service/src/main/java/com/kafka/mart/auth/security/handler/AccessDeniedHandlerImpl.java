package com.kafka.mart.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccessDeniedHandlerImpl
        implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        response.setContentType(
                "application/json"
        );

        response.setStatus(
                HttpServletResponse.SC_FORBIDDEN
        );

        Map<String, Object> body =
                new HashMap<>();

        body.put(
                "success",
                false
        );

        body.put(
                "message",
                "Access Denied"
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