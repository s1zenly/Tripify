package com.tripify.info.client;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.tripify.info.config.CountryInfoProperties;
import com.tripify.info.exception.InfoErrorCode;
import com.tripify.info.exception.InfoServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Component
public class OpenMeteoClient {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final HttpClient weatherHttpClient;
    private final CountryInfoProperties properties;
    private final JsonMapper jsonMapper;

    public OpenMeteoClient(
            @Qualifier("weatherHttpClient") HttpClient weatherHttpClient,
            CountryInfoProperties properties,
            JsonMapper jsonMapper
    ) {
        this.weatherHttpClient = weatherHttpClient;
        this.properties = properties;
        this.jsonMapper = jsonMapper;
    }

    public OpenMeteoResponse fetchDailyForecast(
            double latitude,
            double longitude,
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        CountryInfoProperties.Weather weather = properties.weather();
        String query = "latitude=" + latitude
                + "&longitude=" + longitude
                + "&daily=temperature_2m_mean,precipitation_sum,weather_code"
                + "&timezone=auto"
                + "&start_date=" + encode(dateFrom.format(DATE_FORMAT))
                + "&end_date=" + encode(dateTo.format(DATE_FORMAT));

        URI uri = URI.create(weather.apiUrl() + "?" + query);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofMillis(weather.readTimeoutMs()))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = weatherHttpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() >= 400) {
                throw new InfoServiceException(
                        InfoErrorCode.WEATHER_FORECAST_FAILED,
                        "Open-Meteo request failed with status " + response.statusCode()
                );
            }

            OpenMeteoResponse parsed = jsonMapper.readValue(response.body(), OpenMeteoResponse.class);
            if (parsed.daily() == null
                    || parsed.daily().time() == null
                    || parsed.daily().time().isEmpty()) {
                throw new InfoServiceException(
                        InfoErrorCode.WEATHER_FORECAST_FAILED,
                        "Open-Meteo returned empty daily forecast"
                );
            }

            return parsed;
        } catch (InfoServiceException exception) {
            throw exception;
        } catch (IOException | InterruptedException exception) {
            if (exception instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new InfoServiceException(
                    InfoErrorCode.WEATHER_FORECAST_FAILED,
                    "Failed to load weather forecast from Open-Meteo",
                    exception
            );
        }
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
