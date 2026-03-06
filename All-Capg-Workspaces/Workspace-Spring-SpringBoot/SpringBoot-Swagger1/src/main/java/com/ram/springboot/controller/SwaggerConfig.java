package com.ram.springboot.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
            .info(new Info().title("My API Documentation")
            .description("Spring Boot with Swagger using springdoc-openapi:Hello")
            .version("1.0.0"));
    }
}
