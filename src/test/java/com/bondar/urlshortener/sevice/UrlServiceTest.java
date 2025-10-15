package com.bondar.urlshortener.sevice;

import com.bondar.urlshortener.entity.ShortUrl;
import com.bondar.urlshortener.repository.UrlRepository;
import com.bondar.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.InvalidUrlException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private UrlRepository repository;

    @InjectMocks
    private UrlService service;

    @Test
    void shouldGenerateShortUrlSuccessfully() {
        String originalUrl = "https://google.com";
        ShortUrl saved = new ShortUrl();
        saved.setShortCode("abc123");
        saved.setOriginalUrl(originalUrl);

        when(repository.save(any())).thenReturn(saved);

        ShortUrl result = service.shortenUrl(originalUrl);

        assertThat(result).isEqualTo("abc123");
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionForInvalidUrl() {
        assertThrows(InvalidUrlException.class, () ->
                service.shortenUrl("invalid-url")
        );
    }
}
