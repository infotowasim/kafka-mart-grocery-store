package com.kafka.mart.auth.exception;

import com.kafka.mart.auth.dto.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {

        handler =
                new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFoundSuccess() {

        ResponseEntity<ApiResponse> response =
                handler.handleResourceNotFound(
                        new ResourceNotFoundException(
                                "User Not Found"
                        )
                );

        assertEquals(
                404,
                response.getStatusCode().value()
        );
    }

    @Test
    void handleDuplicateResourceSuccess() {

        ResponseEntity<ApiResponse> response =
                handler.handleDuplicateResource(
                        new DuplicateResourceException(
                                "Duplicate"
                        )
                );

        assertEquals(
                409,
                response.getStatusCode().value()
        );
    }

    @Test
    void handleUnauthorizedSuccess() {

        ResponseEntity<ApiResponse> response =
                handler.handleUnauthorized(
                        new UnauthorizedException(
                                "Unauthorized"
                        )
                );

        assertEquals(
                401,
                response.getStatusCode().value()
        );
    }

    @Test
    void handleForbiddenSuccess() {

        ResponseEntity<ApiResponse> response =
                handler.handleForbidden(
                        new ForbiddenException(
                                "Forbidden"
                        )
                );

        assertEquals(
                403,
                response.getStatusCode().value()
        );
    }

    @Test
    void handleBadRequestSuccess() {

        ResponseEntity<ApiResponse> response =
                handler.handleBadRequest(
                        new BadRequestException(
                                "Bad Request"
                        )
                );

        assertEquals(
                400,
                response.getStatusCode().value()
        );
    }

    @Test
    void handleBadCredentialsSuccess() {

        ResponseEntity<ApiResponse> response =
                handler.handleBadCredentials(
                        new BadCredentialsException(
                                "Invalid"
                        )
                );

        assertEquals(
                401,
                response.getStatusCode().value()
        );

        assertEquals(
                "Invalid email or password",
                response.getBody().getMessage()
        );
    }

    @Test
    void handleGlobalSuccess() {

        ResponseEntity<ApiResponse> response =
                handler.handleGlobal(
                        new RuntimeException(
                                "Something went wrong"
                        )
                );

        assertEquals(
                500,
                response.getStatusCode().value()
        );
    }
}