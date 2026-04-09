/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.web.dto;

import com.capg.ayush.auth.domain.Role;

import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for requesting an update to an existing user's profile or status.
 */
public class UpdateUserRequest {

	private Role role;

	private Boolean enabled;

	@Size(max = 120)
	private String firstName;

	@Size(max = 120)
	private String lastName;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
}
