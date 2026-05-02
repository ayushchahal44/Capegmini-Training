/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.notification.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.capg.ayush.notification.service.NotificationService;
import com.capg.ayush.notification.dto.NotificationDto;

/**
 * REST Controller for querying notification logs.
 * Only accessible by administrators.
 */
@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "Endpoints for viewing notification history and logs")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Retrieves all notification logs.
     * @return List of notification DTOs
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all notification logs")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    /**
     * Retrieves notification logs for a specific application.
     * @param applicationId The application ID
     * @return List of notification DTOs
     */
    @GetMapping("/application/{applicationId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get notifications for a specific application")
    public List<NotificationDto> getNotificationsForApplication(@PathVariable Long applicationId) {
        return notificationService.getNotificationsForApplication(applicationId);
    }
}
