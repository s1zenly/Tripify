package com.tripify.pack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PackRevisionMaintenanceScheduler {

    private final PackRevisionService packRevisionService;

    @Scheduled(fixedDelayString = "${tripify.pack.expired-revisions-check-delay:PT5M}")
    public void failExpiredWaitingRevisions() {
        int failedCount = packRevisionService.failExpiredWaitingRevisions();
        if (failedCount > 0) {
            log.info("Marked {} expired pack revisions as FAILED", failedCount);
        }
    }
}
