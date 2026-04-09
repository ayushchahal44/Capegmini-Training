/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.capg.ayush.document.domain.DocType;
import com.capg.ayush.document.service.DocumentService;
import com.capg.ayush.document.web.dto.DocumentDto;
import com.capg.ayush.document.web.dto.VerifyDocumentRequest;

import jakarta.validation.Valid;

/**
 * REST Controller for managing KYC documents.
 * Provides endpoints for document upload, verification, and retrieval.
 */
@RestController
@RequestMapping("/api/documents")
@Tag(name = "Documents", description = "Endpoints for KYC document upload and verification")
public class DocumentController {

	private final DocumentService documentService;

	/**
	 * Constructs a new DocumentController.
	 * @param documentService The service handling document logic
	 */
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	/**
	 * Uploads a new KYC document for a loan application.
	 * Only accessible by applicants.
	 * @param applicationId The associated loan application ID
	 * @param docType The type of document (e.g., ID_PROOF)
	 * @param file The file being uploaded
	 * @return The uploaded document metadata
	 */
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('APPLICANT')")
	@Operation(summary = "Upload a KYC document")
	public DocumentDto upload(@RequestParam Long applicationId, @RequestParam DocType docType,
			@RequestParam("file") MultipartFile file) {
		return documentService.upload(applicationId, docType, file);
	}

	@PutMapping("/{id}/verify")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Verify or reject a document (Admin/Approver)")
	public DocumentDto verify(@PathVariable Long id, @Valid @RequestBody VerifyDocumentRequest request,
			@RequestHeader("Authorization") String auth) {
		return documentService.verify(id, request, auth);
	}

	@GetMapping("/application/{applicationId}")
	@PreAuthorize("hasAnyRole('APPLICANT','ADMIN')")
	@Operation(summary = "List documents for an application")
	public List<DocumentDto> listForApplication(@PathVariable Long applicationId) {
		return documentService.listForApplication(applicationId);
	}
}
