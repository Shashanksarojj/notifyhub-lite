package com.notifyhub.dto;

import com.notifyhub.enums.NotificationStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponse {

    private Long id;
    private String recipient;
    private String message;
    private String type;
    private NotificationStatus status;
    private int retryCount;
}
