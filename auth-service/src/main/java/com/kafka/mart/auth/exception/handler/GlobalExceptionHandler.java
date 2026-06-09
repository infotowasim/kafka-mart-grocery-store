package com.kafka.mart.auth.exception.handler;


import com.kafka.mart.auth.exception.*;
import com.kafka.mart.auth.payload.ApiErrorPayload;
import com.kafka.mart.auth.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorPayload> handleResourceNotFound(
            ResourceNotFoundException ex) {

        log.warn(
                "Resource not found : {}",
                ex.getMessage()
        );


        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiErrorPayload.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .errorCode("NOT_FOUND")
                                .timestamp(DateTimeUtil.now())
                                .build()
                );

    }



    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorPayload> handleDuplicateResource(
            DuplicateResourceException ex) {

        log.warn(
                "Duplicate resource : {}",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ApiErrorPayload.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .errorCode("CONFLICT")
                                .timestamp(DateTimeUtil.now())
                                .build()
                );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorPayload> handleUnauthorized(
            UnauthorizedException ex) {

        log.warn(
                "Unauthorized access : {}",
                ex.getMessage()
        );


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiErrorPayload.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .errorCode("UNAUTHORIZED")
                                .timestamp(DateTimeUtil.now())
                                .build()
                );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorPayload> handleForbidden(
            ForbiddenException ex) {

        log.warn(
                "Forbidden access : {}",
                ex.getMessage()
        );


        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ApiErrorPayload.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .errorCode("FORBIDDEN")
                                .timestamp(DateTimeUtil.now())
                                .build()
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorPayload> handleBadRequest(
            BadRequestException ex) {

        log.warn(
                "Bad request : {}",
                ex.getMessage()
        );


        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiErrorPayload.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .errorCode("BAD_REQUEST")
                                .timestamp(DateTimeUtil.now())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorPayload> handleValidation(
            MethodArgumentNotValidException ex
    ) {

        FieldError fieldError =
                ex.getBindingResult()
                        .getFieldError();

        String message =
                fieldError != null
                        ? fieldError.getDefaultMessage()
                        : "Validation failed";

        log.warn(
                "Validation failed : {}",
                message
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiErrorPayload.builder()
                                .success(false)
                                .message(message)
                                .errorCode("VALIDATION_ERROR")
                                .timestamp(DateTimeUtil.now())
                                .build()
                );
    }



    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorPayload> handleBadCredentials(
            BadCredentialsException ex) {

        log.warn(
                "Invalid login credentials"
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiErrorPayload.builder()
                                .success(false)
                                .message("Invalid email or password")
                                .errorCode("INVALID_CREDENTIALS")
                                .timestamp(DateTimeUtil.now())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorPayload> handleGlobal(
            Exception ex) {

        log.error(
                "Unhandled exception occurred",
                ex
        );

        return ResponseEntity.status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(
                        ApiErrorPayload.builder()
                                .success(false)
                                .message("Something went wrong")
                                .errorCode("INTERNAL_SERVER_ERROR")
                                .timestamp(DateTimeUtil.now())
                                .build()
                );
    }


}