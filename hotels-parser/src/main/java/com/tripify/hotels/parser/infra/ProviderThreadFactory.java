package com.tripify.hotels.parser.infra;

import org.jspecify.annotations.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ProviderThreadFactory implements ThreadFactory {

    private final String poolName;
    private final AtomicInteger counter = new AtomicInteger(1);

    public ProviderThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(@NonNull Runnable task) {
        Thread thread = new Thread(task);
        thread.setName(poolName + "-" + counter.getAndIncrement());
        return thread;
    }
}
