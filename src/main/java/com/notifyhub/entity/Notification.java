package com.notifyhub.entity;



import com.notifyhub.enums.NotificationStatus;
import com.notifyhub.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type; // EMAIL, SMS, WEBHOOK

    @Enumerated(EnumType.STRING)
    private NotificationStatus status; // PENDING, SENT, FAILED

    private int retryCount;

    private String failureReason;

    private int maxRetries;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}