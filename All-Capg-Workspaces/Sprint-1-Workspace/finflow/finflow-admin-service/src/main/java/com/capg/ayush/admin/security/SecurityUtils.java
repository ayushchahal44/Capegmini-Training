/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for security-related operations in the admin service.
 */
public final class SecurityUtils {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private SecurityUtils() {
	}

	/**
	 * Extracts the current authenticated user's ID from the security context.
	 * @return The user ID as a Long
	 * @throws IllegalStateException if the user is not authenticated
	 */
	public static Long currentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal() == null) {
			throw new IllegalStateException("Not authenticated");
		}
		return Long.parseLong(authentication.getPrincipal().toString());
	}
}
