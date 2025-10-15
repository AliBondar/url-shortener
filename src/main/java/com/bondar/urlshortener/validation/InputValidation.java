package com.bondar.urlshortener.validation;


import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class InputValidation {

    public void validateUrl(String url) {
        try {
            URI uri = new URI(url);
            if (!("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))) {
                throw new IllegalArgumentException("Only HTTP and HTTPS URLs are allowed");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL format");
        }
    }
}
