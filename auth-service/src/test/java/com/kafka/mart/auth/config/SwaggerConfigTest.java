package com.kafka.mart.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void customOpenApiSuccess() {

        SwaggerConfig config =
                new SwaggerConfig();

        OpenAPI openAPI =
                config.customOpenAPI();

        assertNotNull(openAPI);

        assertEquals(
                "Kafka Mart Auth Service",
                openAPI.getInfo().getTitle()
        );

        assertEquals(
                "1.0",
                openAPI.getInfo().getVersion()
        );
    }
}