package com.tripify.notification.service.handler.contract;

import com.tripify.notification.service.model.InvalidMessagePolicy;

public interface NotificationHandler<T> {

    InvalidMessagePolicy invalidMessagePolicy();

    void handle(T event);
}