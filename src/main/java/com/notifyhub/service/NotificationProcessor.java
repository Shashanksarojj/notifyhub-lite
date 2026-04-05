package com.notifyhub.service;

import com.notifyhub.entity.Notification;
import com.notifyhub.enums.NotificationStatus;
import com.notifyhub.repository.NotificationRepository;
import com.notifyhub.service.sender.NotificationSender;
import com.notifyhub.service.sender.NotificationSenderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProcessor {

    private final NotificationSenderFactory senderFactory;
    private final NotificationRepository repository;

    @Async("notificationExecutor")
    @Transactional
    public void process(Long notificationId) {

        log.info("[NotificationID={}] Processing started", notificationId);

        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> {
                    log.error("[NotificationID={}] Not found in DB", notificationId);
                    return new RuntimeException("Notification not found");
                });

        try {
            log.info("[NotificationID={}] Fetch successful. Type={}, Recipient={}",
                    notification.getId(),
                    notification.getType(),
                    notification.getRecipient());

            NotificationSender sender =
                    senderFactory.getSender(notification.getType());

            log.info("[NotificationID={}] Sender resolved: {}",
                    notification.getId(),
                    sender.getClass().getSimpleName());

            // 🔥 actual send
            sender.send(notification);

            notification.setStatus(NotificationStatus.SENT);

            log.info("[NotificationID={}] Notification sent successfully",
                    notification.getId());

        } catch (Exception ex) {

            log.error("[NotificationID={}] Sending failed. Reason={}",
                    notification.getId(),
                    ex.getMessage(),
                    ex);

            notification.setStatus(NotificationStatus.FAILED);
            notification.setFailureReason(ex.getMessage());
        }

        notification.setUpdatedAt(LocalDateTime.now());

        repository.save(notification);

        log.info("[NotificationID={}] Status updated to {} and saved to DB",
                notification.getId(),
                notification.getStatus());
    }
}