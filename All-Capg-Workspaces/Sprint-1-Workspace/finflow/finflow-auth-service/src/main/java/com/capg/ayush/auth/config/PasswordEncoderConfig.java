/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration for password hashing.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Provides a BCryptPasswordEncoder bean for secure password storage.
     * @return The PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
