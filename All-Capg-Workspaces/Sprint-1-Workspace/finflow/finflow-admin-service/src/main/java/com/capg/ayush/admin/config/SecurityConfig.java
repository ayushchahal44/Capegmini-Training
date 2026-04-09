/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capg.ayush.finflow.common.web.jwt.JwtAuthenticationFilter;

/**
 * Security configuration for the Admin Service.
 * Configures JWT filter, stateless sessions, and endpoint-level authorization.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * Constructs a new SecurityConfig.
	 * @param jwtAuthenticationFilter The filter used for JWT validation
	 */
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	/**
	 * Configures the HTTP security filter chain.
	 * @param http The HttpSecurity object to configure
	 * @return The configured SecurityFilterChain
	 * @throws Exception if an error occurs during configuration
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/actuator/**").permitAll()
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/admin/api-docs/**").permitAll()
				.anyRequest().authenticated());
		http.addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/**
	 * Configures the WebSecurity ignore list for public resources (Swagger/OpenAPI).
	 * @return The configured WebSecurityCustomizer
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(
				"/swagger-ui/**",
				"/v3/api-docs/**",
				"/api/admin/api-docs/**"
		);
	}
}
