/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.admin.web.dto;

import com.capg.ayush.admin.domain.Role;

/**
 * Data Transfer Object representing a user's profile information.
 */
public class UserResponse {

	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private Role role;
	private boolean enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
