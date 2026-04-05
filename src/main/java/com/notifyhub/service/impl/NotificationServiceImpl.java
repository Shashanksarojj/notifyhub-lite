package com.notifyhub.service.impl;

import com.notifyhub.dto.NotificationRequest;
import com.notifyhub.dto.NotificationResponse;
import com.notifyhub.entity.Notification;
import com.notifyhub.enums.NotificationStatus;
import com.notifyhub.repository.NotificationRepository;
import com.notifyhub.service.NotificationProcessor;
import com.notifyhub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationProcessor notificationProcessor;

    private NotificationResponse mapToResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .recipient(n.getRecipient())
                .message(n.getMessage())
                .type(n.getType().name())
                .status(n.getStatus())
                .retryCount(n.getRetryCount())
                .build();
    }

    @Override
    @Transactional
    public NotificationResponse createNotification(NotificationRequest request) {

        Notification notification = Notification.builder()
                .recipient(request.getRecipient())
                .message(request.getMessage())
                .type(request.getType())
                .status(NotificationStatus.PENDING)
                .retryCount(0)
                .maxRetries(3)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);

        // ⚠️ async call OUTSIDE DB logic (important)
        notificationProcessor.process(saved.getId());

        return NotificationResponse.builder()
                .id(saved.getId())
                .status(saved.getStatus())
                .message("Notification accepted for processing")
                .build();
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}