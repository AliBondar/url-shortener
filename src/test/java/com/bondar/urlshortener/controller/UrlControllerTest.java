package com.bondar.urlshortener.controller;

import com.bondar.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
public class UrlControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UrlService service;

//    @Test
//    void shouldShortenUrl() throws Exception {
//        when(service.shortenUrl(anyString()))
//                .thenReturn("abc123");
//
//        mockMvc.perform(post("/api/shorten")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .("{\"url\":\"https://google.com\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.shortCode").value("abc123"));
    }

//    @Test
//    void shouldReturn404ForExpiredLink() throws Exception {
//        when(service.getOriginalUrl("abc123"))
//                .thenThrow(new UrlExpiredException("Link expired"));
//
//        mockMvc.perform(get("/abc123"))
//                .andExpect(status().isNotFound());
//    }
}
