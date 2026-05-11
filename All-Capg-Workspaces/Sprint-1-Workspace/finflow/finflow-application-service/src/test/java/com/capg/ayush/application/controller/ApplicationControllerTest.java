package com.capg.ayush.application.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.capg.ayush.application.dto.LoanApplicationDto;
import com.capg.ayush.application.service.ApplicationService;
import com.capg.ayush.finflow.common.web.jwt.JwtAuthenticationFilter;

@WebMvcTest(ApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)
@SuppressWarnings("null")
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private com.capg.ayush.finflow.common.jwt.JwtUtil jwtUtil;

    @Test
    void createDraftSuccess() throws Exception {
        LoanApplicationDto dto = new LoanApplicationDto();
        dto.setId(1L);
        dto.setStatus(com.capg.ayush.application.entity.ApplicationStatus.DRAFT);

        when(applicationService.createDraft()).thenReturn(dto);

        mockMvc.perform(post("/api/applications/draft")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("DRAFT"));
    }

    @Test
    void myApplicationsSuccess() throws Exception {
        LoanApplicationDto dto = new LoanApplicationDto();
        dto.setId(1L);
        
        when(applicationService.myApplications()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/applications/my")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getApplicationSuccess() throws Exception {
        LoanApplicationDto dto = new LoanApplicationDto();
        dto.setId(1L);

        when(applicationService.getApplicationById(anyLong())).thenReturn(dto);

        mockMvc.perform(get("/api/applications/1")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
