/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.ayush.auth.service.AuthService;
import com.capg.ayush.auth.web.dto.UpdateUserRequest;
import com.capg.ayush.auth.web.dto.UserResponse;

import jakarta.validation.Valid;

/**
 * REST Controller for administrative user management.
 * Provides endpoints for listing all users and updating user accounts.
 */
@RestController
@RequestMapping("/api/auth/users")
public class UserAdminController {

	private final AuthService authService;

	/**
	 * Constructs a new UserAdminController.
	 * @param authService The service handling user management logic
	 */
	public UserAdminController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * Retrieves a list of all registered users.
	 * Only accessible by administrators.
	 * @return A list of UserResponse objects
	 */
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> listUsers() {
		return authService.listUsers();
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
		return authService.updateUser(id, request);
	}
}
