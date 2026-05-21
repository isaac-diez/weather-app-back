package com.isaac.weatherapp.client;

import com.isaac.weatherapp.dto.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@Slf4j
public class WeatherApiClient {

    private final WebClient weatherWebClient;

    public WeatherApiClient(@Qualifier("weatherWebClient") WebClient weatherWebClient) {
        this.weatherWebClient = weatherWebClient;
    }

    public WeatherResponse getWeatherData(double latitude, double longitude) {

            return weatherWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/forecast")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current", "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,cloud_cover,wind_speed_10m,wind_direction_10m,wind_gusts_10m,uv_index")
                            .queryParam("hourly", "temperature_2m,weather_code,rain,precipitation_probability,cloud_cover,uv_index,uv_index_clear_sky,sunshine_duration,shortwave_radiation,wind_speed_10m,wind_direction_10m,wind_gusts_10m")
                            .queryParam("daily", "temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,precipitation_probability_max,uv_index_max,uv_index_clear_sky_max,sunshine_duration,daylight_duration,sunrise,sunset")
                            .queryParam("timezone", "auto")
                            .queryParam("forecast_hours", 24)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(WeatherResponse.class)
                    .block();
        }
}
