package com.tripify.info.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.tripify.info.config.CountryInfoProperties;
import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TutuGeoClient {

    private final HttpClient tutuHttpClient;
    private final CountryInfoProperties properties;

    public String downloadPage(String sourceUrl) {
        CountryInfoProperties.Scraping scraping = properties.scraping();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(sourceUrl))
                .timeout(Duration.ofMillis(scraping.readTimeoutMs()))
                .header("User-Agent", scraping.userAgent())
                .header("Accept", "text/html,application/xhtml+xml")
                .GET()
                .build();

        try {
            HttpResponse<String> response = tutuHttpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() >= 400) {
                throw new InfoServiceException(
                        InfoErrorCode.TUTU_PAGE_DOWNLOAD_FAILED,
                        "Tutu page download failed with status " + response.statusCode() + " for url=" + sourceUrl
                );
            }

            return response.body();
        } catch (InfoServiceException exception) {
            throw exception;
        } catch (IOException | InterruptedException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new InfoServiceException(
                    InfoErrorCode.TUTU_PAGE_DOWNLOAD_FAILED,
                    "Failed to download Tutu page for url=" + sourceUrl,
                    exception
            );
        }
    }
}
