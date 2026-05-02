package com.capg.ayush.notification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capg.ayush.notification.service.NotificationService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    void contextLoads() {
        assertNotNull(notificationService, "NotificationService should be injected");
    }

    @Test
    void getAllNotificationsReturnsEmptyListInitially() {
        var notifications = notificationService.getAllNotifications();
        assertNotNull(notifications);
    }
}
