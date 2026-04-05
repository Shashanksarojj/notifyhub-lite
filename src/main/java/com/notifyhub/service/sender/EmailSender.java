package com.notifyhub.service.sender;

import com.notifyhub.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailSender implements NotificationSender {

    @Override
    public void send(Notification notification) {
        log.info("[NotificationID={}] Sending EMAIL to {}",
                notification.getId(),
                notification.getRecipient());

        // simulate API call
        sleep();

        log.info("[NotificationID={}] EMAIL sent successfully",
                notification.getId());
    }

    private void sleep() {
        try { Thread.sleep(1000); } catch (Exception ignored) {}
    }
}