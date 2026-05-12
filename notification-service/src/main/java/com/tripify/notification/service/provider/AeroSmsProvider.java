package com.tripify.notification.service.provider;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.tripify.notification.service.config.property.AeroSmsProperties;
import com.tripify.notification.service.dto.AeroSmsRequest;
import com.tripify.notification.service.dto.AeroSmsResponse;
import com.tripify.notification.service.exception.ProviderUnavailableException;
import com.tripify.notification.service.model.NotificationChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class AeroSmsProvider implements NotificationProvider<String, String> {

    private static final String AERO_SMS_URL = "https://gate.smsaero.ru/v2/sms/send";

    private final RestClient restClient;
    private final AeroSmsProperties properties;

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.SMS;
    }

    @Override
    public String name() {
        return properties.name();
    }

    @Override
    public void send(String destination, String payload) {

        if (!properties.enabled()) {
            log.warn("{} disabled", properties.name());
            return;
        }

        try {

            String auth = properties.email() + ":" + properties.apiKey();

            AeroSmsRequest request = new AeroSmsRequest(
                    destination,
                    payload,
                    properties.sign()
            );

            AeroSmsResponse response = restClient.post()
                    .uri(AERO_SMS_URL)
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            "Basic " + Base64.getEncoder()
                                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8))
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(AeroSmsResponse.class);

            if (response == null || !response.success()) {
                throw new ProviderUnavailableException(
                        "SmsAero returned unsuccessful response: "
                                + (response != null ? response.message() : "null")
                );
            }

            log.info("SMS sent via {} to {}", properties.name(), destination);

        } catch (Exception ex) {
            throw new ProviderUnavailableException("SmsAero send failed", ex);
        }
    }
}