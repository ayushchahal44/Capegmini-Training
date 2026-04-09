/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.web.dto;

import java.time.Instant;

import com.capg.ayush.document.domain.DocStatus;
import com.capg.ayush.document.domain.DocType;

/**
 * Data Transfer Object for document metadata.
 */
public class DocumentDto {

	private Long id;
	private Long applicationId;
	private Long userId;
	private DocType docType;
	private String originalName;
	private DocStatus status;
	private Instant createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public DocType getDocType() {
		return docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public DocStatus getStatus() {
		return status;
	}

	public void setStatus(DocStatus status) {
		this.status = status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
