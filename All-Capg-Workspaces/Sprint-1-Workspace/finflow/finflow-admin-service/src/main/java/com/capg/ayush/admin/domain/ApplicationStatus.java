/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.domain;

/**
 * Enumeration of various states a loan application can be in.
 */
public enum ApplicationStatus {
	DRAFT,
	SUBMITTED,
	DOCS_PENDING,
	DOCS_VERIFIED,
	UNDER_REVIEW,
	APPROVED,
	REJECTED,
	CLOSED
}
