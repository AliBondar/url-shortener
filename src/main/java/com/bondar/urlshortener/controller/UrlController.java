package com.bondar.urlshortener.controller;

import com.bondar.urlshortener.entity.ShortUrl;
import com.bondar.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> body) {
        String originalUrl = body.get("url");
        ShortUrl shortUrl = urlService.shortenUrl(originalUrl);

        String baseUrl = "http://localhost:8080/s/";
        return ResponseEntity.ok(Map.of("shortUrl", baseUrl + shortUrl.getShortCode()));
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirectToOriginal(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }

    @GetMapping("/version")
    public ResponseEntity<Map<String, String>> getVersion() {
        Map<String, String> versionInfo = new HashMap<>();
        versionInfo.put("version", "1.0.1");
        versionInfo.put("description", "URL Shortener Service");
        versionInfo.put("author", "Ali Bondar");
        versionInfo.put("date", LocalDateTime.now().toString());
        return ResponseEntity.ok(versionInfo);
    }

}
