package com.capg.ayush.admin.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.capg.ayush.admin.dto.AdminDecisionRequest;
import com.capg.ayush.admin.dto.LoanApplicationDto;
import com.capg.ayush.admin.dto.ReportResponse;
import com.capg.ayush.admin.dto.UserResponse;
import com.capg.ayush.admin.dto.VerifyDocumentRequest;
import com.capg.ayush.admin.service.AdminService;
import com.capg.ayush.finflow.common.web.jwt.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {
    private static final String AUTH_HEADER = "Authorization";
    private static final String MOCK_TOKEN = "Bearer mock-token";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private com.capg.ayush.finflow.common.jwt.JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void applicationsSuccess() throws Exception {
        LoanApplicationDto dto = new LoanApplicationDto();
        dto.setId(1L);

        when(adminService.applicationQueue(anyString())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/admin/applications")
                .header(AUTH_HEADER, MOCK_TOKEN)
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void usersSuccess() throws Exception {
        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(adminService.listUsers(anyString())).thenReturn(List.of(user));

        mockMvc.perform(get("/api/admin/users")
                .header(AUTH_HEADER, MOCK_TOKEN)
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    void decideSuccess() throws Exception {
        AdminDecisionRequest request = new AdminDecisionRequest();
        request.setApproved(true);
        request.setTerms("Standard interest");

        doNothing().when(adminService).decide(anyLong(), any(AdminDecisionRequest.class), anyString());

        mockMvc.perform(put("/api/admin/applications/1/decision")
                .header(AUTH_HEADER, MOCK_TOKEN)
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk());
    }

    @Test
    void reportsSuccess() throws Exception {
        ReportResponse report = new ReportResponse();
        report.setTotalApplications(10L);
        report.setTotalRecordedDecisions(5L);
        report.setApprovedDecisions(3L);
        report.setRejectedDecisions(2L);
        report.setApplicationsByStatus(Map.of("DRAFT", 5L, "APPROVED", 5L));

        when(adminService.reports(anyString())).thenReturn(report);

        mockMvc.perform(get("/api/admin/reports")
                .header(AUTH_HEADER, MOCK_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalApplications").value(10))
                .andExpect(jsonPath("$.totalRecordedDecisions").value(5));
    }

    @Test
    void verifyDocumentSuccess() throws Exception {
        VerifyDocumentRequest request = new VerifyDocumentRequest();
        request.setVerified(true);

        doNothing().when(adminService).verifyDocument(anyLong(), any(VerifyDocumentRequest.class), anyString());

        mockMvc.perform(put("/api/admin/documents/1/verify")
                .header(AUTH_HEADER, MOCK_TOKEN)
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserSuccess() throws Exception {
        com.capg.ayush.admin.dto.UpdateUserRequest request = new com.capg.ayush.admin.dto.UpdateUserRequest();

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setEmail("updated@test.com");

        when(adminService.updateUser(anyLong(), any(com.capg.ayush.admin.dto.UpdateUserRequest.class), anyString()))
                .thenReturn(response);

        mockMvc.perform(put("/api/admin/users/1")
                .header(AUTH_HEADER, MOCK_TOKEN)
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
