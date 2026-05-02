/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration for the Notification Service.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI notificationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FinFlow Notification Service API")
                        .description("Endpoints for viewing notification logs and event history")
                        .version("1.0.0"));
    }
}
