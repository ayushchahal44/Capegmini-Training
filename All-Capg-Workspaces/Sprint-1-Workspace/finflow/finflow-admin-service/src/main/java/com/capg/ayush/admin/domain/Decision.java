/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity representing an administrative decision on a loan application.
 */
@Entity
@Table(name = "decisions")
public class Decision {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "application_id", nullable = false)
	private Long applicationId;

	@Column(nullable = false)
	private boolean approved;

	@Column(length = 2000)
	private String terms;

	@Column(name = "rejection_reason", length = 2000)
	private String rejectionReason;

	@Column(name = "decided_by_user_id", nullable = false)
	private Long decidedByUserId;

	@Column(name = "decided_at", nullable = false)
	private Instant decidedAt = Instant.now();

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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Long getDecidedByUserId() {
		return decidedByUserId;
	}

	public void setDecidedByUserId(Long decidedByUserId) {
		this.decidedByUserId = decidedByUserId;
	}

	public Instant getDecidedAt() {
		return decidedAt;
	}

	public void setDecidedAt(Instant decidedAt) {
		this.decidedAt = decidedAt;
	}
}
