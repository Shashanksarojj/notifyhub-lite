package com.notifyhub.service.sender;

import com.notifyhub.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSenderFactory {

    private final EmailSender emailSender;
    private final SmsSender smsSender;
    private final WebhookSender webhookSender;

    public NotificationSender getSender(NotificationType type) {
        return switch (type) {
            case EMAIL -> emailSender;
            case SMS -> smsSender;
            case WEBHOOK -> webhookSender;
        };
    }
}