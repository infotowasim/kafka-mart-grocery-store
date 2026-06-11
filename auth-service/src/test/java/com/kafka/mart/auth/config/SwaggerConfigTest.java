package com.kafka.mart.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    private final SwaggerConfig swaggerConfig =
            new SwaggerConfig();

    @Test
    void customOpenAPI_shouldReturnConfiguredOpenAPI() {

        OpenAPI openAPI =
                swaggerConfig.customOpenAPI();

        assertNotNull(openAPI);

        assertEquals(
                "Kafka Mart Auth Service",
                openAPI.getInfo().getTitle()
        );

        assertEquals(
                "1.0",
                openAPI.getInfo().getVersion()
        );

        assertNotNull(
                openAPI.getComponents()
        );

        assertTrue(
                openAPI.getComponents()
                        .getSecuritySchemes()
                        .containsKey(
                                "bearerAuth"
                        )
        );

        SecurityScheme securityScheme =
                openAPI.getComponents()
                        .getSecuritySchemes()
                        .get("bearerAuth");

        assertEquals(
                SecurityScheme.Type.HTTP,
                securityScheme.getType()
        );

        assertEquals(
                "bearer",
                securityScheme.getScheme()
        );

        assertEquals(
                "JWT",
                securityScheme.getBearerFormat()
        );
    }
}