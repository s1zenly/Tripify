package com.tripify.notification.service.sender;

import com.tripify.notification.service.model.NotificationChannel;

public interface NotificationSender {

    void send(NotificationChannel channel, String destination, String payload);
}
