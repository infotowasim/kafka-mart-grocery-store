package com.kafka.mart.auth.exception.handler;

import com.kafka.mart.auth.exception.*;
import com.kafka.mart.auth.payload.ApiErrorPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {

        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFound_shouldReturn404() {

        ResponseEntity<ApiErrorPayload> response =
                handler.handleResourceNotFound(
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        assertEquals(
                404,
                response.getStatusCode().value()
        );

        assertEquals(
                "NOT_FOUND",
                response.getBody().getErrorCode()
        );
    }

    @Test
    void handleDuplicateResource_shouldReturn409() {

        ResponseEntity<ApiErrorPayload> response =
                handler.handleDuplicateResource(
                        new DuplicateResourceException(
                                "Email already exists"
                        )
                );

        assertEquals(
                409,
                response.getStatusCode().value()
        );

        assertEquals(
                "CONFLICT",
                response.getBody().getErrorCode()
        );
    }

    @Test
    void handleUnauthorized_shouldReturn401() {

        ResponseEntity<ApiErrorPayload> response =
                handler.handleUnauthorized(
                        new UnauthorizedException(
                                "Unauthorized"
                        )
                );

        assertEquals(
                401,
                response.getStatusCode().value()
        );

        assertEquals(
                "UNAUTHORIZED",
                response.getBody().getErrorCode()
        );
    }

    @Test
    void handleForbidden_shouldReturn403() {

        ResponseEntity<ApiErrorPayload> response =
                handler.handleForbidden(
                        new ForbiddenException(
                                "Forbidden"
                        )
                );

        assertEquals(
                403,
                response.getStatusCode().value()
        );

        assertEquals(
                "FORBIDDEN",
                response.getBody().getErrorCode()
        );
    }

    @Test
    void handleBadRequest_shouldReturn400() {

        ResponseEntity<ApiErrorPayload> response =
                handler.handleBadRequest(
                        new BadRequestException(
                                "Bad request"
                        )
                );

        assertEquals(
                400,
                response.getStatusCode().value()
        );

        assertEquals(
                "BAD_REQUEST",
                response.getBody().getErrorCode()
        );
    }

    @Test
    void handleBadCredentials_shouldReturn401() {

        ResponseEntity<ApiErrorPayload> response =
                handler.handleBadCredentials(
                        new BadCredentialsException(
                                "Invalid credentials"
                        )
                );

        assertEquals(
                401,
                response.getStatusCode().value()
        );

        assertEquals(
                "INVALID_CREDENTIALS",
                response.getBody().getErrorCode()
        );
    }

    @Test
    void handleGlobal_shouldReturn500() {

        ResponseEntity<ApiErrorPayload> response =
                handler.handleGlobal(
                        new RuntimeException(
                                "Unexpected error"
                        )
                );

        assertEquals(
                500,
                response.getStatusCode().value()
        );

        assertEquals(
                "INTERNAL_SERVER_ERROR",
                response.getBody().getErrorCode()
        );
    }

    @Test
    void handleValidation_shouldReturnValidationError() {

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(
                        new Object(),
                        "object"
                );

        bindingResult.addError(
                new FieldError(
                        "object",
                        "email",
                        "Email is required"
                )
        );

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(
                        null,
                        bindingResult
                );

        ResponseEntity<ApiErrorPayload> response =
                handler.handleValidation(
                        exception
                );

        assertEquals(
                400,
                response.getStatusCode().value()
        );

        assertEquals(
                "VALIDATION_ERROR",
                response.getBody().getErrorCode()
        );

        assertEquals(
                "Email is required",
                response.getBody().getMessage()
        );
    }


    @Test
    void handleValidation_shouldReturnDefaultMessage() {

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(
                        new Object(),
                        "object"
                );

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(
                        null,
                        bindingResult
                );

        ResponseEntity<ApiErrorPayload> response =
                handler.handleValidation(
                        exception
                );

        assertEquals(
                400,
                response.getStatusCode().value()
        );

        assertEquals(
                "Validation failed",
                response.getBody().getMessage()
        );

        assertEquals(
                "VALIDATION_ERROR",
                response.getBody().getErrorCode()
        );
    }
}