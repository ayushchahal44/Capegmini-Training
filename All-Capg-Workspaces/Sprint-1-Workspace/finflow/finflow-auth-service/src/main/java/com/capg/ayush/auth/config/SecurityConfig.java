/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capg.ayush.finflow.common.web.jwt.JwtAuthenticationFilter;

/**
 * Security configuration for the Auth Service.
 * Configures JWT authentication, stateless sessions, and public/private endpoints.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * Constructs a new SecurityConfig.
	 * @param jwtAuthenticationFilter The filter used for JWT validation
	 */
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		log.debug("SecurityConfig initialized with {}", jwtAuthenticationFilter);
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		
		return new InMemoryUserDetailsManager();
	}

	/**
	 * Configures the HTTP security filter chain.
	 * @param http The HttpSecurity object to configure
	 * @return The configured SecurityFilterChain
	 * @throws Exception if an error occurs during configuration
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/signup").permitAll()
				.requestMatchers("/api/auth/login").permitAll()
				.requestMatchers("/actuator/**").permitAll()
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/auth/api-docs/**").permitAll()
				.anyRequest().authenticated());
		http.addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(
				"/swagger-ui/**",
				"/v3/api-docs/**",
				"/api/auth/api-docs/**"
		);
	}
}
