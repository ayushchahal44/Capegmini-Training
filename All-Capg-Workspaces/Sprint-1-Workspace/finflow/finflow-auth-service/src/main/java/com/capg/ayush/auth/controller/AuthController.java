/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.ayush.auth.service.AuthService;
import com.capg.ayush.auth.dto.AuthResponse;
import com.capg.ayush.auth.dto.LoginRequest;
import com.capg.ayush.auth.dto.SignupRequest;

import jakarta.validation.Valid;

/**
 * REST Controller for authentication operations.
 * Provides endpoints for user registration (signup) and authentication (login).
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user signup and login")
public class AuthController {

	private final AuthService authService;

	/**
	 * Constructs a new AuthController.
	 * @param authService The service handling authentication logic
	 */
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * Registers a new user account.
	 * @param request The signup request details
	 * @return An AuthResponse with the JWT token
	 */
	@PostMapping("/signup")
	@Operation(summary = "Register a new user", description = "Creates a new user account and returns a JWT token")
	public AuthResponse signup(@Valid @RequestBody SignupRequest request) {
		return authService.signup(request);
	}

	@PostMapping("/login")
	@Operation(summary = "Authenticate user", description = "Verifies credentials and returns a JWT token")
	public AuthResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@org.springframework.web.bind.annotation.GetMapping("/internal/users/{id}/email")
	@Operation(summary = "Internal: Get user email by ID", description = "Used by notification service")
	public org.springframework.http.ResponseEntity<java.util.Map<String, String>> getUserEmail(@org.springframework.web.bind.annotation.PathVariable Long id) {
		com.capg.ayush.auth.dto.UserResponse user = authService.listUsers().stream()
				.filter(u -> u.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));
		return org.springframework.http.ResponseEntity.ok(java.util.Map.of("email", user.getEmail()));
	}
}
