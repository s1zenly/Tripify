package com.tripify.notification.service.provider;


import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tripify.notification.service.model.NotificationChannel;
import org.springframework.stereotype.Component;

@Component
public class ProviderRegistry {

    private final Map<NotificationChannel, List<NotificationProvider<String, String>>> providers;

    public ProviderRegistry(List<NotificationProvider<String, String>> providerList) {
        this.providers = providerList.stream()
                .collect(Collectors.groupingBy(
                        NotificationProvider::channel,
                        () -> new EnumMap<>(NotificationChannel.class),
                        Collectors.toList()
                ));
    }

    public List<NotificationProvider<String, String>> getProviders(NotificationChannel channel) {
        List<NotificationProvider<String, String>> providersByChannel = providers.get(channel);

        if (providersByChannel.isEmpty()) {
            throw new IllegalStateException("Providers not found for channel: " + channel);
        }

        return providersByChannel;
    }
}
