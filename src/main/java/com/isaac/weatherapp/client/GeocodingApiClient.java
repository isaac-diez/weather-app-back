package com.isaac.weatherapp.client;

import com.isaac.weatherapp.dto.CityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class GeocodingApiClient {

    private final WebClient geocodingWebClient;

    public GeocodingApiClient(@Qualifier("geocodingWebClient") WebClient geocodingWebClient) {
        this.geocodingWebClient = geocodingWebClient;
    }

    public CityResponse getCityData(String city) {
        log.info("Calling GeoCoding API for city: {}", city);

        return geocodingWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search")
                        .queryParam("name", city)
                        .queryParam("count", 20)
                        .build())
                .retrieve()
                .bodyToMono(CityResponse.class)
                .block();
    }
}
