package com.bondar.urlshortener.service;

import com.bondar.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlCleanupScheduler {

    private final UrlRepository urlRepository;

    @Scheduled(cron = "0 0 */6 * * *")
    public void cleanupExpiredUrls() {
        Instant now = Instant.now();
        int deleted = urlRepository.deleteByExpiryDateBefore(now);
        if (deleted > 0) {
            log.info("Deleted {} expired URLs at {}", deleted, now);
        }
    }
}
