package com.capg.ayush.notification.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.capg.ayush.notification.dto.NotificationDto;
import com.capg.ayush.notification.service.NotificationService;
import com.capg.ayush.finflow.common.web.jwt.JwtAuthenticationFilter;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
@SuppressWarnings("null")
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private com.capg.ayush.finflow.common.jwt.JwtUtil jwtUtil;

    @Test
    void getAllNotificationsSuccess() throws Exception {
        NotificationDto dto = new NotificationDto();
        dto.setId(1L);
        dto.setApplicationId(10L);
        dto.setUserId(100L);
        dto.setEventType("SUBMITTED");
        dto.setChannel("EMAIL");
        dto.setMessage("Test notification");
        dto.setSent(true);
        dto.setCreatedAt(Instant.now());

        when(notificationService.getAllNotifications()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].eventType").value("SUBMITTED"));
    }

    @Test
    void getAllNotificationsEmpty() throws Exception {
        when(notificationService.getAllNotifications()).thenReturn(List.of());

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getNotificationsForApplicationSuccess() throws Exception {
        NotificationDto dto = new NotificationDto();
        dto.setId(2L);
        dto.setApplicationId(10L);
        dto.setEventType("APPROVED");

        when(notificationService.getNotificationsForApplication(10L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/notifications/application/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].eventType").value("APPROVED"));
    }

    @Test
    void getNotificationsForApplicationNotFound() throws Exception {
        when(notificationService.getNotificationsForApplication(999L)).thenReturn(List.of());

        mockMvc.perform(get("/api/notifications/application/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
