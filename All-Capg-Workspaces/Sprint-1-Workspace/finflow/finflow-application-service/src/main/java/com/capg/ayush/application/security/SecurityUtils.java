/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.application.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for security-related operations in the application service.
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

	/**
	 * Checks if the current authenticated user has the specified role.
	 * @param role The role to check (e.g., "ADMIN")
	 * @return true if the user has the role, false otherwise
	 */
	public static boolean hasRole(String role) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		return authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
	}
}
