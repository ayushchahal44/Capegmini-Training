/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI/Swagger documentation for the Document Service.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI bean with service-specific information and security schemes.
     * @return The configured OpenAPI instance
     */
    @Bean
    public OpenAPI documentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FinFlow Document Service")
                        .description("KYC Document Management API")
                        .version("0.0.1"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
