package com.notifyhub.service.sender;

import com.notifyhub.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsSender implements NotificationSender {

    @Override
    public void send(Notification notification) {
        log.info("Sending SMS to {}", notification.getRecipient());
        sleep();
        log.info("SMS sent successfully");
    }

    private void sleep() {
        try { Thread.sleep(800); } catch (Exception ignored) {}
    }
}