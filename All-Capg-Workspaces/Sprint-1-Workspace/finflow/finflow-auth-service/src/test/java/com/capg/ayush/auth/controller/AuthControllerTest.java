package com.capg.ayush.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.capg.ayush.auth.dto.AuthResponse;
import com.capg.ayush.auth.dto.LoginRequest;
import com.capg.ayush.auth.dto.SignupRequest;
import com.capg.ayush.auth.dto.UserResponse;
import com.capg.ayush.auth.entity.Role;
import com.capg.ayush.auth.service.AuthService;
import com.capg.ayush.finflow.common.web.jwt.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@SuppressWarnings("null")
public class AuthControllerTest {
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password123";
    private static final String MOCK_TOKEN = "mock-token";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private com.capg.ayush.finflow.common.jwt.JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signupSuccess() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setEmail(TEST_EMAIL);
        request.setPassword(TEST_PASSWORD);
        request.setFirstName("John");
        request.setLastName("Doe");

        AuthResponse response = new AuthResponse(MOCK_TOKEN, null);

        when(authService.signup(any(SignupRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/signup")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(MOCK_TOKEN));
    }

    @Test
    void loginSuccess() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail(TEST_EMAIL);
        request.setPassword(TEST_PASSWORD);

        AuthResponse response = new AuthResponse(MOCK_TOKEN, null);

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(MOCK_TOKEN));
    }

    @Test
    void signupInvalidEmailShouldReturnBadRequest() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setEmail("invalid-email"); // Invalid email format
        request.setPassword(TEST_PASSWORD);
        request.setFirstName("John");
        request.setLastName("Doe");

        mockMvc.perform(post("/api/auth/signup")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserEmailSuccess() throws Exception {
        UserResponse userResponse = new UserResponse(1L, TEST_EMAIL, "John", "Doe", Role.APPLICANT, true);
        when(authService.listUsers()).thenReturn(List.of(userResponse));

        mockMvc.perform(get("/api/auth/internal/users/1/email"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(TEST_EMAIL));
    }

    @Test
    void getUserEmailNotFound() throws Exception {
        when(authService.listUsers()).thenReturn(List.of());

        mockMvc.perform(get("/api/auth/internal/users/999/email"))
                .andExpect(status().isNotFound());
    }
}
