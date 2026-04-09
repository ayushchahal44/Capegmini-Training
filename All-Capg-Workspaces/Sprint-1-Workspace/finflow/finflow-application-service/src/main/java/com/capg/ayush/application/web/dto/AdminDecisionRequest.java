/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.web.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for submitting an administrative decision on a loan application.
 */
public class AdminDecisionRequest {

	@NotNull
	private Boolean approved;

	private String terms;

	private String rejectionReason;

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
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
}
