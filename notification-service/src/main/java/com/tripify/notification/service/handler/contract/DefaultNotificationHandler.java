package com.tripify.notification.service.handler.contract;

public abstract class DefaultNotificationHandler<T> implements NotificationHandler<T>{

    protected abstract void validate(T event);
}
