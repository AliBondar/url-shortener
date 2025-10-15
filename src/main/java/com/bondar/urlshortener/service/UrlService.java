package com.bondar.urlshortener.service;

import com.bondar.urlshortener.entity.ShortUrl;
import com.bondar.urlshortener.repository.UrlRepository;
import com.bondar.urlshortener.validation.InputValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final InputValidation inputValidation;

    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;

    @Transactional
    public ShortUrl shortenUrl(String originalUrl) {
        //validation
        inputValidation.validateUrl(originalUrl);

        // check if already exists and still active
        Optional<ShortUrl> existing = urlRepository.findByOriginalUrl(originalUrl);
        if (existing.isPresent() && existing.get().isActive()) {
            return existing.get();
        }

        String shortCode = generateShortCode();

        ShortUrl shortUrl = ShortUrl.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .createdAt(LocalDateTime.now())
                .expiryAt(LocalDateTime.now().plusDays(30)) // configurable
                .active(true)
                .accessCount(0)
                .build();

        return urlRepository.save(shortUrl);
    }

    /**
     * Expands short code to original URL, updates access count.
     */
    @Transactional
    public String getOriginalUrl(String shortCode) {
        ShortUrl shortUrl = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        if (!shortUrl.isActive() || shortUrl.getExpiryAt().isBefore(LocalDateTime.now())) {
            shortUrl.setActive(false);
            urlRepository.save(shortUrl);
            throw new RuntimeException("Short URL expired");
        }

        shortUrl.setAccessCount(shortUrl.getAccessCount() + 1);
        urlRepository.save(shortUrl);

        return shortUrl.getOriginalUrl();
    }

    /**
     * Utility to generate random 6-character Base62 code.
     */
    private String generateShortCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int idx = random.nextInt(BASE62.length());
            sb.append(BASE62.charAt(idx));
        }

        // Ensure uniqueness
        if (urlRepository.findByShortCode(sb.toString()).isPresent()) {
            return generateShortCode(); // retry if collision
        }

        return sb.toString();
    }
}
