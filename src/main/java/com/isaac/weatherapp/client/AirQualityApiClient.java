package com.isaac.weatherapp.client;

import com.isaac.weatherapp.dto.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@Slf4j
public class AirQualityApiClient {

    private final WebClient airQualityWebClient;

    public AirQualityApiClient(@Qualifier("airQualityWebClient") WebClient airQualityWebClient) {
        this.airQualityWebClient = airQualityWebClient;
    }

    public WeatherResponse getWeatherData(double latitude, double longitude) {

        return airQualityWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("current", "wind_speed_10m,wind_direction_10m,wind_gusts_10m,uv_index,temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,cloud_cover")
                        .queryParam("daily", "apparent_temperature_min,precipitation_probability_max,apparent_temperature_max")
                        .queryParam("hourly", "temperature_2m,weather_code,rain,precipitation_probability")
                        .queryParam("timezone", "auto")
                        .queryParam("forecast_hours",24)
                        .queryParam("past_hours",1)
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .block();
    }
}
