package com.bondar.urlshortener.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Component
public class SecurityConfig {

    private static final String API_KEY = "my-secret-api-key";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/shorten").authenticated()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
