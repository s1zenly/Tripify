package com.tripify.auth.service.infra;

import java.time.Clock;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeProvider {

    private final Clock clock;

    public Instant nowUtc() {
        return Instant.now(clock);
    }
}
