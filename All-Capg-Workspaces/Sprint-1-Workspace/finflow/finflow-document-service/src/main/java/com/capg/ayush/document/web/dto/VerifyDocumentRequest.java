/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.web.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for document verification requests.
 */
public class VerifyDocumentRequest {

	@NotNull
	private Boolean verified;

	private String notes;

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
