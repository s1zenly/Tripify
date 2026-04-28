package com.tripify.hotels.parser.service;

import com.google.common.util.concurrent.RateLimiter;
import com.tripify.hotels.parser.infra.ProviderThreadFactory;
import com.tripify.hotels.parser.models.ProviderConfig;
import lombok.Getter;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

@SuppressWarnings("UnstableApiUsage")
public class ProviderExecutionContext {

    @Getter
    private final int batchSize;
    private final Executor executorService;
    private final RateLimiter rateLimiter;

    public ProviderExecutionContext(ProviderConfig providerConfig) {
        this.executorService = new ThreadPoolExecutor(
                providerConfig.corePoolSize(),
                providerConfig.maxPoolSize(),
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(providerConfig.queueSize()),
                new ProviderThreadFactory(providerConfig.poolName()),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        this.rateLimiter = RateLimiter.create(providerConfig.rps());
        this.batchSize = providerConfig.batchSize();
    }

    public <T> CompletableFuture<T> submit(Supplier<T> task) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return CompletableFuture.supplyAsync(() -> {
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }

            try {
                rateLimiter.acquire();
                return task.get();
            } finally {
                MDC.clear();
            }
        }, executorService);
    }
}
