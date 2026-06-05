package com.kafka.mart.auth.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionsTest {

    @Test
    void badRequestExceptionSuccess() {

        BadRequestException ex =
                new BadRequestException(
                        "Bad Request"
                );

        assertEquals(
                "Bad Request",
                ex.getMessage()
        );
    }

    @Test
    void duplicateResourceExceptionSuccess() {

        DuplicateResourceException ex =
                new DuplicateResourceException(
                        "Duplicate Resource"
                );

        assertEquals(
                "Duplicate Resource",
                ex.getMessage()
        );
    }

    @Test
    void forbiddenExceptionSuccess() {

        ForbiddenException ex =
                new ForbiddenException(
                        "Forbidden"
                );

        assertEquals(
                "Forbidden",
                ex.getMessage()
        );
    }

    @Test
    void resourceNotFoundExceptionSuccess() {

        ResourceNotFoundException ex =
                new ResourceNotFoundException(
                        "Not Found"
                );

        assertEquals(
                "Not Found",
                ex.getMessage()
        );
    }

    @Test
    void unauthorizedExceptionSuccess() {

        UnauthorizedException ex =
                new UnauthorizedException(
                        "Unauthorized"
                );

        assertEquals(
                "Unauthorized",
                ex.getMessage()
        );
    }
}