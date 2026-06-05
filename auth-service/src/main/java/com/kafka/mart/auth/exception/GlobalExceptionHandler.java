package com.kafka.mart.auth.exception;

import com.kafka.mart.auth.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse> handleDuplicateResource(
            DuplicateResourceException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse> handleUnauthorized(
            UnauthorizedException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse> handleForbidden(
            ForbiddenException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequest(
            BadRequestException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(message)
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(
            BadCredentialsException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(
                                        "Invalid email or password"
                                )
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobal(
            Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );
    }
}