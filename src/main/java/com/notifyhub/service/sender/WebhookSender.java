package com.notifyhub.service.sender;

import com.notifyhub.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebhookSender implements NotificationSender {

    @Override
    public void send(Notification notification) {
        log.info("Calling webhook {}", notification.getRecipient());
        sleep();
        log.info("Webhook delivered");
    }

    private void sleep() {
        try { Thread.sleep(1200); } catch (Exception ignored) {}
    }
}