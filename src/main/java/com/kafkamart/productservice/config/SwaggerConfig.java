package com.kafkamart.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("KafkaMart Product Service API")
                        .version("1.0")
                        .description("Product Service APIs for KafkaMart Grocery Application")
                        .contact(new Contact()
                                .name("KafkaMart Team")
                                .email("support@kafkamart.com")));
    }
}