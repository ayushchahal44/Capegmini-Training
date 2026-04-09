/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.web.dto;

import com.capg.ayush.auth.domain.Role;

/**
 * Data Transfer Object representing a user profile in responses.
 */
public class UserResponse {

	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private Role role;
	private boolean enabled;

	public UserResponse() {
	}

	public UserResponse(Long id, String email, String firstName, String lastName, Role role, boolean enabled) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.enabled = enabled;
	}

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
