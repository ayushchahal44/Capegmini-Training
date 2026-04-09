/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.web.dto;

/**
 * Internal request DTO for notifying that documents have been verified.
 */
public class NotifyDocVerifiedRequest {

	private boolean allRequiredVerified;

	public boolean isAllRequiredVerified() {
		return allRequiredVerified;
	}

	public void setAllRequiredVerified(boolean allRequiredVerified) {
		this.allRequiredVerified = allRequiredVerified;
	}
}
