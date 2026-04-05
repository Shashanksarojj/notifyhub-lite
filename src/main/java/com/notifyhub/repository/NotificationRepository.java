package com.notifyhub.repository;

import com.notifyhub.entity.Notification;
import com.notifyhub.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByStatusAndRetryCountLessThan(
            NotificationStatus status,
            int retryCount
    );
}
