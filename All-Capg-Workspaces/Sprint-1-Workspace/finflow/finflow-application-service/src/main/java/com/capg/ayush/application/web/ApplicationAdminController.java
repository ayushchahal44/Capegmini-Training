/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.web;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.capg.ayush.application.service.ApplicationService;
import com.capg.ayush.application.web.dto.AdminDecisionRequest;
import com.capg.ayush.application.web.dto.AdminStatsDto;
import com.capg.ayush.application.web.dto.LoanApplicationDto;
import com.capg.ayush.application.web.dto.NotifyDocVerifiedRequest;

import jakarta.validation.Valid;

/**
 * REST Controller for administrative management of loan applications.
 * Provides endpoints for reviewing the queue, viewing stats, and applying decisions.
 */
@RestController
@RequestMapping("/api/applications/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin – Applications", description = "Back-office endpoints for managing loan applications")
public class ApplicationAdminController {

	private final ApplicationService applicationService;

	/**
	 * Constructs a new ApplicationAdminController.
	 * @param applicationService The service handling loan application logic
	 */
	public ApplicationAdminController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	/**
	 * Retrieves the queue of all loan applications for administrative review.
	 * @return A list of loan application DTOs
	 */
	@GetMapping("/queue")
	@Operation(summary = "Get all applications for review")
	public List<LoanApplicationDto> queue() {
		return applicationService.adminQueue();
	}

	/**
	 * Retrieves statistics on loan application statuses.
	 * @return An AdminStatsDto containing aggregated counts
	 */
	@GetMapping("/stats")
	@Operation(summary = "Get statistics on application statuses")
	public AdminStatsDto stats() {
		return applicationService.adminStats();
	}

	/**
	 * Applies an administrative decision (Approve/Reject) to a loan application.
	 * @param id The ID of the application
	 * @param request The decision details
	 */
	@PutMapping("/{id}/decision")
	@Operation(summary = "Apply an approval/rejection decision")
	public void decide(@PathVariable Long id, @Valid @RequestBody AdminDecisionRequest request) {
		applicationService.applyAdminDecision(id, request);
	}

	@PutMapping("/{id}/review")
	@Operation(summary = "Move a document-verified application to under-review")
	public void moveToReview(@PathVariable Long id) {
		applicationService.moveToUnderReview(id);
	}

	@PutMapping("/{id}/notify-verified")
	@Operation(summary = "Internal: Notify that all documents are verified")
	public void notifyVerified(@PathVariable Long id, @RequestBody NotifyDocVerifiedRequest req) {
		applicationService.notifyDocumentsVerified(id, req);
	}
}
