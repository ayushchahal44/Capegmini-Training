/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.capg.ayush.document.client.ApplicationServiceClient;
import com.capg.ayush.document.dto.DocumentDto;
import com.capg.ayush.document.dto.VerifyDocumentRequest;
import com.capg.ayush.document.entity.DocStatus;
import com.capg.ayush.document.entity.DocType;
import com.capg.ayush.document.entity.DocumentEntity;
import com.capg.ayush.document.messaging.DocumentStatusChangedEvent;
import com.capg.ayush.document.repository.DocumentRepository;
import com.capg.ayush.document.security.SecurityUtils;
import com.capg.ayush.document.config.RabbitMQConfig;

/**
 * Service class for managing KYC documents.
 * Handles document uploads, verification, and listing for specific applications.
 */
@Service
public class DocumentService {

	private static final EnumSet<DocType> REQUIRED_TYPES = EnumSet.allOf(DocType.class);

	private final DocumentRepository documentRepository;
	private final ApplicationServiceClient applicationServiceClient;
	private final RabbitTemplate rabbitTemplate;

	@Value("${finflow.storage.root:uploads}")
	private String storageRoot;

	/**
	 * Constructs a new DocumentService.
	 * @param documentRepository Repository for document metadata
	 * @param applicationServiceClient Client for communicating with the application service
	 */
	public DocumentService(DocumentRepository documentRepository, 
			ApplicationServiceClient applicationServiceClient,
			RabbitTemplate rabbitTemplate) {
		this.documentRepository = documentRepository;
		this.applicationServiceClient = applicationServiceClient;
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * Uploads and stores a new KYC document.
	 * @param applicationId The ID of the loan application the document belongs to
	 * @param docType The type of document being uploaded
	 * @param file The multipart file data
	 * @return The created document metadata as a DocumentDto
	 * @throws ResponseStatusException if the file is empty or storage fails
	 */
	@Transactional
	@SuppressWarnings("null")
	public DocumentDto upload(Long applicationId, DocType docType, MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is required");
		}
		Long userId = SecurityUtils.currentUserId();
		String original = java.util.Objects.requireNonNullElse(file.getOriginalFilename(), "upload.bin");
		String safeName = UUID.randomUUID() + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
		Path dir = Paths.get(storageRoot, String.valueOf(applicationId));
		try {
			Files.createDirectories(dir);
			Path target = dir.resolve(safeName);
			try (InputStream in = file.getInputStream()) {
				Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
			}

			// Check if a document of this type already exists for this application
			DocumentEntity doc = documentRepository.findTopByApplicationIdAndDocTypeOrderByCreatedAtDesc(applicationId, docType)
					.orElse(new DocumentEntity());
			
			doc.setApplicationId(applicationId);
			doc.setUserId(userId);
			doc.setDocType(docType);
			doc.setOriginalName(original);
			doc.setStoredPath(target.toString().replace("\\", "/"));
			doc.setContentType(file.getContentType());
			doc.setSizeBytes(file.getSize());
			doc.setStatus(DocStatus.PENDING);
			doc.setRejectionReason(null); // Clear any previous rejection reason on re-upload
			return toDto(documentRepository.save(doc));
		}
		catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file");
		}
	}

	/**
	 * Verifies or rejects a specific KYC document.
	 * If all required documents are verified, it notifies the application service.
	 * @param id The ID of the document to verify
	 * @param request The verification decision (approve/reject)
	 * @param authorizationHeader The JWT header to pass to the application service
	 * @return The updated document metadata
	 * @throws ResponseStatusException if the document is not found
	 */
	@Transactional
	@SuppressWarnings("null")
	public DocumentDto verify(Long id, VerifyDocumentRequest request, String authorizationHeader) {
		DocumentEntity doc = documentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		DocStatus oldStatus = doc.getStatus();
		if (Boolean.TRUE.equals(request.getVerified())) {
			doc.setStatus(DocStatus.VERIFIED);
			doc.setRejectionReason(null);
		}
		else {
			doc.setStatus(DocStatus.REJECTED);
			doc.setRejectionReason(request.getNotes());
		}
		documentRepository.save(doc);

		// Publish event if status changed to REJECTED
		if (doc.getStatus() == DocStatus.REJECTED) {
			publishStatusEvent(doc, oldStatus, doc.getStatus(), request.getNotes());
		}

		boolean allDone = allRequiredVerified(doc.getApplicationId());
		if (allDone) {
			applicationServiceClient.notifyDocumentsVerified(doc.getApplicationId(), true, authorizationHeader);
		}
		return toDto(doc);
	}

	private void publishStatusEvent(DocumentEntity doc, DocStatus oldStatus, DocStatus newStatus, String reason) {
		try {
			DocumentStatusChangedEvent event = new DocumentStatusChangedEvent(
					doc.getId(), doc.getApplicationId(), doc.getUserId(), 
					doc.getDocType(), oldStatus, newStatus, reason);
			String routingKey = "document.status." + newStatus.name().toLowerCase();
			rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, event);
		} catch (Exception e) {
			// Non-blocking
		}
	}

	/**
	 * Lists all documents associated with a specific loan application.
	 * @param applicationId The application ID
	 * @return A list of DocumentDto objects
	 */
	@Transactional(readOnly = true)
	public List<DocumentDto> listForApplication(Long applicationId) {
		return documentRepository.findByApplicationIdOrderByCreatedAtDesc(applicationId).stream().map(this::toDto)
				.collect(Collectors.toList());
	}

	private boolean allRequiredVerified(Long applicationId) {
		for (DocType t : REQUIRED_TYPES) {
			if (!documentRepository.existsByApplicationIdAndDocTypeAndStatus(applicationId, t, DocStatus.VERIFIED)) {
				return false;
			}
		}
		return true;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("null")
	public org.springframework.http.ResponseEntity<org.springframework.core.io.Resource> download(Long id) {
		DocumentEntity doc = documentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		Long userId = SecurityUtils.currentUserId();
		if (!SecurityUtils.hasRole("ADMIN") && !doc.getUserId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		try {
			Path file = Paths.get(doc.getStoredPath());
			org.springframework.core.io.Resource resource = new org.springframework.core.io.UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return org.springframework.http.ResponseEntity.ok()
						.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getOriginalName() + "\"")
						.contentType(org.springframework.http.MediaType.parseMediaType(doc.getContentType()))
						.body(resource);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not read file");
			}
		} catch (java.net.MalformedURLException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file", e);
		}
	}

	private DocumentDto toDto(DocumentEntity d) {
		DocumentDto dto = new DocumentDto();
		dto.setId(d.getId());
		dto.setApplicationId(d.getApplicationId());
		dto.setUserId(d.getUserId());
		dto.setDocType(d.getDocType());
		dto.setOriginalName(d.getOriginalName());
		dto.setStatus(d.getStatus());
		dto.setCreatedAt(d.getCreatedAt());
		dto.setRejectionReason(d.getRejectionReason());
		return dto;
	}
}
