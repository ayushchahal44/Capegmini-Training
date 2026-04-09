/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.capg.ayush.admin.service.AdminService;
import com.capg.ayush.admin.web.dto.AdminDecisionRequest;
import com.capg.ayush.admin.web.dto.LoanApplicationDto;
import com.capg.ayush.admin.web.dto.ReportResponse;
import com.capg.ayush.admin.web.dto.UpdateUserRequest;
import com.capg.ayush.admin.web.dto.UserResponse;

import jakarta.validation.Valid;

/**
 * REST Controller for administrative operations.
 * Provides endpoints for managing loan applications, users, and reports.
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Aggregation endpoints for back-office admin workflows")
public class AdminController {

	private final AdminService adminService;

	/**
	 * Constructs a new AdminController.
	 * @param adminService The service handling administrative logic
	 */
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * Retrieves all loan applications in the queue.
	 * @param auth The authorization token
	 * @return A list of loan application DTOs
	 */
	@GetMapping("/applications")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get loan application queue from application-service")
	public List<LoanApplicationDto> applications(@RequestHeader("Authorization") String auth) {
		return adminService.applicationQueue(auth);
	}

	/**
	 * Applies a decision (Approve/Reject) to a loan application.
	 * @param id The ID of the application
	 * @param request The decision details
	 * @param auth The authorization token
	 */
	@PutMapping("/applications/{id}/decision")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Apply decision via application-service and record it locally")
	public void decide(@PathVariable Long id, @Valid @RequestBody AdminDecisionRequest request,
			@RequestHeader("Authorization") String auth) {
		adminService.decide(id, request, auth);
	}

		/**
	 * Retrieves aggregated reports across services.
	 * @param auth The authorization token
	 * @return A ReportResponse containing system statistics
	 */
	@GetMapping("/reports")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get aggregated reports across services")
	public ReportResponse reports(@RequestHeader("Authorization") String auth) {
		return adminService.reports(auth);
	}

		/**
	 * Lists all users registered in the system.
	 * @param auth The authorization token
	 * @return A list of user profile DTOs
	 */
	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "List all users from auth-service")
	public List<UserResponse> users(@RequestHeader("Authorization") String auth) {
		return adminService.listUsers(auth);
	}

	/**
	 * Updates a user's profile and status.
	 * @param id The ID of the user to update
	 * @param request The update details
	 * @param auth The authorization token
	 * @return The updated user profile DTO
	 */
	@PutMapping("/users/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update user properties (role, enabled) via auth-service")
	public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request,
			@RequestHeader("Authorization") String auth) {
		return adminService.updateUser(id, request, auth);
	}
}
