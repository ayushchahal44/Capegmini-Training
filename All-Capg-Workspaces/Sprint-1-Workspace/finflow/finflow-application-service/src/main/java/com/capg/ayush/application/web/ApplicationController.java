/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.capg.ayush.application.service.ApplicationService;
import com.capg.ayush.application.web.dto.ApplicationStatusResponse;
import com.capg.ayush.application.web.dto.LoanApplicationDto;
import com.capg.ayush.application.web.dto.UpdateLoanApplicationRequest;

import jakarta.validation.Valid;

/**
 * REST Controller for applicant-facing loan application operations.
 * Allows users to create, update, and track their own applications.
 */
@RestController
@RequestMapping("/api/applications")
@Tag(name = "Loan Applications", description = "Endpoints for applicants to manage their loan applications")
public class ApplicationController {

	private final ApplicationService applicationService;

	/**
	 * Constructs a new ApplicationController.
	 * @param applicationService The service handling loan application logic
	 */
	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	/**
	 * Creates a new draft loan application for the authenticated applicant.
	 * @return The created loan application DTO
	 */
	@PostMapping("/draft")
	@PreAuthorize("hasRole('APPLICANT')")
	@Operation(summary = "Create a new draft application")
	public LoanApplicationDto createDraft() {
		return applicationService.createDraft();
	}

	/**
	 * Updates an existing draft loan application.
	 * @param id The ID of the application to update
	 * @param req The updated application details
	 * @return The updated loan application DTO
	 */
	@PutMapping("/{id}/draft")
	@PreAuthorize("hasRole('APPLICANT')")
	@Operation(summary = "Update an existing draft application")
	public LoanApplicationDto updateDraft(@PathVariable Long id, @Valid @RequestBody UpdateLoanApplicationRequest req) {
		return applicationService.updateDraft(id, req);
	}

	/**
	 * Submits a draft loan application for review.
	 * @param id The ID of the application to submit
	 * @return The submitted loan application DTO
	 */
	@PostMapping("/{id}/submit")
	@PreAuthorize("hasRole('APPLICANT')")
	@Operation(summary = "Submit a draft application")
	public LoanApplicationDto submit(@PathVariable Long id) {
		return applicationService.submit(id);
	}

	@GetMapping("/my")
	@PreAuthorize("hasRole('APPLICANT')")
	@Operation(summary = "List current user's applications")
	public List<LoanApplicationDto> myApplications() {
		return applicationService.myApplications();
	}

	@GetMapping("/{id}/status")
	@PreAuthorize("hasRole('APPLICANT')")
	@Operation(summary = "Get detailed status and timeline of an application")
	public ApplicationStatusResponse status(@PathVariable Long id) {
		return applicationService.statusForApplicant(id);
	}
}
