/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.document.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for security-related operations.
 * Provides methods to retrieve information about the currently authenticated user.
 */
public final class SecurityUtils {

	/**
	 * Private constructor to prevent instantiation of the utility class.
	 */
	private SecurityUtils() {
	}

	/**
	 * Retrieves the ID of the currently authenticated user from the security context.
	 * @return The current user's ID
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
