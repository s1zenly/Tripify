package com.tripify.notification.service.sender;

import java.util.List;

import com.tripify.notification.service.exception.ProviderUnavailableException;
import com.tripify.notification.service.model.NotificationChannel;
import com.tripify.notification.service.provider.NotificationProvider;
import com.tripify.notification.service.provider.ProviderHealthRegistry;
import com.tripify.notification.service.provider.ProviderRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultNotificationSender implements NotificationSender {

    private final ProviderRegistry providerRegistry;
    private final ProviderHealthRegistry healthRegistry;

    @Override
    public void send(NotificationChannel channel, String destination, String payload) {
        List<NotificationProvider<String, String>> providers =
                providerRegistry.getProviders(channel);

        RuntimeException lastException = null;

        for (NotificationProvider<String, String> provider : providers) {
            if (!healthRegistry.isAvailable(provider)) {
                log.info("Provider {} is disabled, skip", provider.name());
                continue;
            }

            try {
                provider.send(destination, payload);
                return;

            } catch (ProviderUnavailableException ex) {
                lastException = ex;
                healthRegistry.disableTemporarily(provider);

                log.warn("Provider {} failed, switching to next", provider.name(), ex);

            } catch (RuntimeException ex) {
                lastException = ex;

                log.warn("Unknown provider error in {}, switching to next", provider.name(), ex);
            }
        }

        throw new IllegalStateException(
                "All providers failed for channel: " + channel,
                lastException
        );
    }
}
