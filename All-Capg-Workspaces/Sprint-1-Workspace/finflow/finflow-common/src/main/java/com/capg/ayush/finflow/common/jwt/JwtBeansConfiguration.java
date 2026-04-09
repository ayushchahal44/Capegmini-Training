/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for enabling JWT-related properties.
 */
@Configuration
@EnableConfigurationProperties(FinflowJwtProperties.class)
public class JwtBeansConfiguration {
}
