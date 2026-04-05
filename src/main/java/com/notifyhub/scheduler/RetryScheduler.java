package com.notifyhub.scheduler;

import com.notifyhub.entity.Notification;
import com.notifyhub.enums.NotificationStatus;
import com.notifyhub.repository.NotificationRepository;
import com.notifyhub.service.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RetryScheduler {

    private final NotificationRepository repository;
    private final NotificationProcessor processor;

    @Scheduled(fixedDelay = 10000)
    public void retryFailedNotifications() {

        log.info("[RetryScheduler] Retry job started");

        List<Notification> failedNotifications =
                repository.findByStatusAndRetryCountLessThan(
                        NotificationStatus.FAILED,
                        3
                );

        if (failedNotifications.isEmpty()) {
            log.info("[RetryScheduler] No failed notifications to retry");
            return;
        }

        log.info("[RetryScheduler] Found {} failed notifications for retry",
                failedNotifications.size());

        for (Notification notification : failedNotifications) {

            try {
                log.info("[RetryScheduler][NotificationID={}] Retrying attempt {}",
                        notification.getId(),
                        notification.getRetryCount() + 1);

                // update retry count + reset state
                notification.setRetryCount(notification.getRetryCount() + 1);
                notification.setStatus(NotificationStatus.PENDING);

                Notification updated = repository.save(notification);

                // async processing
                processor.process(updated.getId());

            } catch (Exception ex) {

                log.error("[RetryScheduler][NotificationID={}] Error while scheduling retry",
                        notification.getId(),
                        ex);
            }
        }

        log.info("[RetryScheduler] Retry job completed");
    }
}