package com.isaac.weatherapp.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("weatherWebClient")
    public WebClient weatherWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    @Bean
    @Qualifier("geocodingWebClient")
    public WebClient geocodingWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://geocoding-api.open-meteo.com")
                .build();
    }

    @Bean
    @Qualifier("airQualityWebClient")
    public WebClient airQualityWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://air-quality-api.open-meteo.com")
                .build();
    }

}
