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

import com.capg.ayush.auth.domain.Role;
import com.capg.ayush.auth.domain.User;
import com.capg.ayush.auth.repo.UserRepository;
import com.capg.ayush.auth.web.dto.AuthResponse;
import com.capg.ayush.auth.web.dto.LoginRequest;
import com.capg.ayush.auth.web.dto.SignupRequest;
import com.capg.ayush.finflow.common.jwt.JwtTokenProvider;

/**
 * Unit tests for {@link AuthService}.
 * Validates signup, login, and user management logic.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

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
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password");
        signupRequest.setFirstName("Test");
        signupRequest.setLastName("User");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPasswordHash("encodedPassword");
        user.setRole(Role.APPLICANT);
        user.setEnabled(true);
    }

    @Test
    void signup_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtTokenProvider.generateToken(anyLong(), anyString(), anyString())).thenReturn("testToken");

        AuthResponse response = authService.signup(signupRequest);

        assertNotNull(response);
        assertEquals("testToken", response.getToken());
        assertEquals("test@example.com", response.getUser().getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void signup_EmailConflict() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> authService.signup(signupRequest));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenProvider.generateToken(anyLong(), anyString(), anyString())).thenReturn("testToken");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("testToken", response.getToken());
    }

    @Test
    void login_InvalidCredentials() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> authService.login(loginRequest));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }

    @Test
    void login_UserDisabled() {
        user.setEnabled(false);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> authService.login(loginRequest));
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
    }

    @Test
    void listUsers_Success() {
        when(userRepository.findAll()).thenReturn(java.util.List.of(user));
        
        java.util.List<com.capg.ayush.auth.web.dto.UserResponse> result = authService.listUsers();
        
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void updateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        com.capg.ayush.auth.web.dto.UpdateUserRequest req = new com.capg.ayush.auth.web.dto.UpdateUserRequest();
        req.setFirstName("Updated");
        req.setRole(Role.ADMIN);
        
        com.capg.ayush.auth.web.dto.UserResponse result = authService.updateUser(1L, req);
        
        assertNotNull(result);
        assertEquals("Updated", user.getFirstName());
        assertEquals(Role.ADMIN, user.getRole());
        verify(userRepository).save(user);
    }
}
