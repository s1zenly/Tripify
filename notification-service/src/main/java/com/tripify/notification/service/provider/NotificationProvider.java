package com.tripify.notification.service.provider;

import com.tripify.notification.service.model.NotificationChannel;

public interface NotificationProvider<D, P> {

    String name();

    NotificationChannel channel();

    void send(D destination, P payload);
}
