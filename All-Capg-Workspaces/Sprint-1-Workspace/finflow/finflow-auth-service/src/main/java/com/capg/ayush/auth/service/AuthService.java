/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.capg.ayush.auth.domain.Role;
import com.capg.ayush.auth.domain.User;
import com.capg.ayush.auth.repo.UserRepository;
import com.capg.ayush.auth.web.dto.AuthResponse;
import com.capg.ayush.auth.web.dto.LoginRequest;
import com.capg.ayush.auth.web.dto.SignupRequest;
import com.capg.ayush.auth.web.dto.UpdateUserRequest;
import com.capg.ayush.auth.web.dto.UserResponse;
import com.capg.ayush.finflow.common.jwt.JwtTokenProvider;

/**
 * Service class for authentication and user management.
 * Handles user registration (signup), login, and profile updates.
 */
@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * Constructs a new AuthService.
	 * @param userRepository Repository for user data
	 * @param passwordEncoder Encoder for password hashing and verification
	 * @param jwtTokenProvider Provider for generating JWT tokens
	 */
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * Registers a new user (applicant) in the system.
	 * @param request The signup request containing user details
	 * @return An AuthResponse containing the JWT token and user profile
	 * @throws ResponseStatusException if the email is already registered
	 */
	@Transactional
	public AuthResponse signup(SignupRequest request) {
		try {
			log.debug("=== SIGNUP DEBUG START ===");
			log.debug("Email: {}", request.getEmail());
			// Do not log password in production.
			log.debug("FirstName: {}", request.getFirstName());
			log.debug("LastName: {}", request.getLastName());
			
			if (userRepository.existsByEmail(request.getEmail())) {
				log.debug("Email already exists");
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
			}
			log.debug("Email check passed");
			
			User user = new User();
			user.setEmail(request.getEmail().trim().toLowerCase());
			log.debug("Set email: {}", user.getEmail());
			
			String encodedPassword = passwordEncoder.encode(request.getPassword());
			user.setPasswordHash(encodedPassword);
			log.debug("Set password hash");
			
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setRole(Role.APPLICANT);
			user.setEnabled(true);
			log.debug("Set user properties");
			
			user = userRepository.save(user);
			log.debug("Saved user with ID: {}", user.getId());
			
			String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRole().name());
			// Avoid logging JWT token in production.
			log.debug("Generated token");
			
			UserResponse userResponse = toResponse(user);
			log.debug("Created user response");
			
			AuthResponse response = new AuthResponse(token, userResponse);
			log.debug("=== SIGNUP DEBUG END ===");
			return response;
		} catch (Exception e) {
			log.error("=== SIGNUP ERROR === {}", e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Authenticates a user and generates a JWT token.
	 * @param request The login request containing credentials
	 * @return An AuthResponse containing the JWT token and user profile
	 * @throws ResponseStatusException if credentials are invalid or the user is disabled
	 */
	public AuthResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
		if (!user.isEnabled()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User disabled");
		}
		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
		}
		String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getRole().name());
		return new AuthResponse(token, toResponse(user));
	}

	@Transactional(readOnly = true)
	public List<UserResponse> listUsers() {
		return userRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	/**
	 * Updates an existing user's profile and roles.
	 * Only accessible by administrators.
	 * @param id The ID of the user to update
	 * @param request The updated user details
	 * @return The updated user details as a UserResponse
	 * @throws ResponseStatusException if the user is not found
	 */
	@Transactional
	public UserResponse updateUser(Long id, UpdateUserRequest request) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (request.getRole() != null) {
			user.setRole(request.getRole());
		}
		if (request.getEnabled() != null) {
			user.setEnabled(request.getEnabled());
		}
		if (request.getFirstName() != null) {
			user.setFirstName(request.getFirstName());
		}
		if (request.getLastName() != null) {
			user.setLastName(request.getLastName());
		}
		return toResponse(userRepository.save(user));
	}

	private UserResponse toResponse(User user) {
		return new UserResponse(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole(),
				user.isEnabled());
	}
}
