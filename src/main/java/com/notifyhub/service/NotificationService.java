package com.notifyhub.service;

import com.notifyhub.dto.NotificationRequest;
import com.notifyhub.dto.NotificationResponse;
import com.notifyhub.entity.Notification;

import java.util.List;

public interface NotificationService {
    NotificationResponse createNotification(NotificationRequest request);
    List<NotificationResponse> getAllNotifications();
}
