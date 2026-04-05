package com.notifyhub.dto;

import com.notifyhub.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotBlank
    private String recipient;

    @NotBlank
    private String message;

    private NotificationType type;
}
