package com.notifyhub.service.sender;

import com.notifyhub.entity.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
