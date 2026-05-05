package com.tripify.hotels.service.service.helper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PhotoDownloadUtils {

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(CONNECT_TIMEOUT)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private PhotoDownloadUtils() {
    }

    public static Optional<byte[]> downloadBytes(String sourceUrl) {
        long start = System.currentTimeMillis();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(sourceUrl))
                    .timeout(REQUEST_TIMEOUT)
                    .GET()
                    .build();

            HttpResponse<byte[]> response = HTTP_CLIENT.send(
                    request,
                    HttpResponse.BodyHandlers.ofByteArray()
            );

            long elapsed = System.currentTimeMillis() - start;

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                log.info("Photo downloaded. url={}, size={}KB, time={}ms",
                        sourceUrl, response.body().length / 1024, elapsed);
                return Optional.of(response.body());
            }

            log.warn("Photo download returned status {}. url={}, time={}ms",
                    response.statusCode(), sourceUrl, elapsed);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            long elapsed = System.currentTimeMillis() - start;
            log.warn("Photo download interrupted. url={}, time={}ms", sourceUrl, elapsed);
        } catch (Exception exception) {
            long elapsed = System.currentTimeMillis() - start;
            log.warn("Failed to download photo, skipping. url={}, reason={}, time={}ms",
                    sourceUrl, exception.getMessage(), elapsed);
        }
        return Optional.empty();
    }

    public static String resolveFileName(String sourceUrl) {
        String path = URI.create(sourceUrl).getPath();
        if (path == null || path.isBlank() || "/".equals(path)) {
            return UUID.randomUUID() + ".jpg";
        }

        String fileName = path.substring(path.lastIndexOf('/') + 1);
        if (fileName.isBlank()) {
            return UUID.randomUUID() + ".jpg";
        }

        return fileName;
    }
}
