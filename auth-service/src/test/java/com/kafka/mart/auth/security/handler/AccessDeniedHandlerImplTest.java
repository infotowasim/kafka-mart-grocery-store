package com.kafka.mart.auth.security.handler;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;

class AccessDeniedHandlerImplTest {

        private final AccessDeniedHandlerImpl handler =
                new AccessDeniedHandlerImpl();

        @Test
        void handle_shouldReturnForbiddenResponse()
                throws Exception {

            MockHttpServletRequest request =
                    new MockHttpServletRequest();

            request.setRequestURI(
                    "/api/v1/admin/users"
            );

            MockHttpServletResponse response =
                    new MockHttpServletResponse();

            AccessDeniedException exception =
                    new AccessDeniedException(
                            "Access denied"
                    );

            handler.handle(
                    request,
                    response,
                    exception
            );

            assertEquals(
                    403,
                    response.getStatus()
            );

            assertEquals(
                    "application/json",
                    response.getContentType()
            );

            String content =
                    response.getContentAsString();

            assertTrue(
                    content.contains("Access Denied")
            );

            assertTrue(
                    content.contains("success")
            );

            assertTrue(
                    content.contains("false")
            );
        }
    }
