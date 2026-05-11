/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.capg.ayush.auth.entity.Role;
import com.capg.ayush.auth.entity.User;
import com.capg.ayush.auth.repository.UserRepository;
import com.capg.ayush.auth.dto.AuthResponse;
import com.capg.ayush.auth.dto.LoginRequest;
import com.capg.ayush.auth.dto.SignupRequest;
import com.capg.ayush.finflow.common.jwt.JwtTokenProvider;

/**
 * Unit tests for {@link AuthService}.
 * Validates signup, login, and user management logic.
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class AuthServiceTest {
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String TEST_TOKEN = "testToken";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setEmail(TEST_EMAIL);
        signupRequest.setPassword(TEST_PASSWORD);
        signupRequest.setFirstName("Test");
        signupRequest.setLastName("User");

        loginRequest = new LoginRequest();
        loginRequest.setEmail(TEST_EMAIL);
        loginRequest.setPassword(TEST_PASSWORD);

        user = new User();
        user.setId(1L);
        user.setEmail(TEST_EMAIL);
        user.setPasswordHash(ENCODED_PASSWORD);
        user.setRole(Role.APPLICANT);
        user.setEnabled(true);
    }

    @Test
    void signupSuccess() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtTokenProvider.generateToken(anyLong(), anyString(), anyString())).thenReturn(TEST_TOKEN);

        AuthResponse response = authService.signup(signupRequest);

        assertNotNull(response);
        assertEquals(TEST_TOKEN, response.getToken());
        assertEquals(TEST_EMAIL, response.getUser().getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void signupEmailConflict() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> authService.signup(signupRequest));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void loginSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenProvider.generateToken(anyLong(), anyString(), anyString())).thenReturn(TEST_TOKEN);

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals(TEST_TOKEN, response.getToken());
    }

    @Test
    void loginInvalidCredentials() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> authService.login(loginRequest));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }

    @Test
    void loginUserDisabled() {
        user.setEnabled(false);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> authService.login(loginRequest));
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
    }

    @Test
    void listUsersSuccess() {
        when(userRepository.findAll()).thenReturn(java.util.List.of(user));
        
        java.util.List<com.capg.ayush.auth.dto.UserResponse> result = authService.listUsers();
        
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void updateUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        com.capg.ayush.auth.dto.UpdateUserRequest req = new com.capg.ayush.auth.dto.UpdateUserRequest();
        req.setFirstName("Updated");
        req.setRole(Role.ADMIN);
        
        com.capg.ayush.auth.dto.UserResponse result = authService.updateUser(1L, req);
        
        assertNotNull(result);
        assertEquals("Updated", user.getFirstName());
        assertEquals(Role.ADMIN, user.getRole());
        verify(userRepository).save(user);
    }

    @Test
    void signupAdminEmailSetsAdminRole() {
        SignupRequest adminRequest = new SignupRequest();
        adminRequest.setEmail("admin@example.com");
        adminRequest.setPassword(TEST_PASSWORD);
        adminRequest.setFirstName("Admin");
        adminRequest.setLastName("User");

        User adminUser = new User();
        adminUser.setId(2L);
        adminUser.setEmail("admin@example.com");
        adminUser.setRole(Role.ADMIN);
        adminUser.setEnabled(true);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(adminUser);
        when(jwtTokenProvider.generateToken(anyLong(), anyString(), anyString())).thenReturn("adminToken");

        AuthResponse response = authService.signup(adminRequest);

        assertNotNull(response);
        assertEquals("adminToken", response.getToken());
    }

    @Test
    void loginUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> authService.login(loginRequest));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }

    @Test
    void updateUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        com.capg.ayush.auth.dto.UpdateUserRequest req = new com.capg.ayush.auth.dto.UpdateUserRequest();
        req.setFirstName("Test");

        assertThrows(ResponseStatusException.class, () -> authService.updateUser(999L, req));
    }

    @Test
    void updateUserAllFields() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        com.capg.ayush.auth.dto.UpdateUserRequest req = new com.capg.ayush.auth.dto.UpdateUserRequest();
        req.setFirstName("New");
        req.setLastName("Name");
        req.setRole(Role.ADMIN);
        req.setEnabled(false);
        
        com.capg.ayush.auth.dto.UserResponse result = authService.updateUser(1L, req);
        
        assertNotNull(result);
        assertEquals("New", user.getFirstName());
        assertEquals("Name", user.getLastName());
        assertEquals(Role.ADMIN, user.getRole());
        assertFalse(user.isEnabled());
    }
}
