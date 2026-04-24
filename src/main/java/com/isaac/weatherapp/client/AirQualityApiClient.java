package com.isaac.weatherapp.client;

import com.isaac.weatherapp.dto.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@Slf4j
public class AirQualityApiClient {

    private final WebClient weatherWebClient;

    public AirQualityApiClient(@Qualifier("weatherWebClient") WebClient weatherWebClient) {
        this.weatherWebClient = weatherWebClient;
    }

    public WeatherResponse getWeatherData(double latitude, double longitude) {

            return weatherWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/air-quality")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current", "european_aqi,pm2_5,pm10,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone,aerosol_optical_depth,dust,ammonia,ragweed_pollen,olive_pollen,mugwort_pollen,grass_pollen,birch_pollen,alder_pollen")
                            .queryParam("hourly", "pm10,pm2_5,carbon_monoxide,carbon_dioxide,nitrogen_dioxide,sulphur_dioxide,ozone,aerosol_optical_depth,dust,ammonia,methane,alder_pollen,birch_pollen,grass_pollen,mugwort_pollen,olive_pollen,ragweed_pollen,european_aqi,european_aqi_pm2_5,european_aqi_pm10,european_aqi_ozone,european_aqi_nitrogen_dioxide,european_aqi_sulphur_dioxide,formaldehyde,glyoxal,non_methane_volatile_organic_compounds,pm10_wildfires,peroxyacyl_nitrates,secondary_inorganic_aerosol,residential_elementary_carbon,total_elementary_carbon,pm2_5_total_organic_matter,sea_salt_aerosol,nitrogen_monoxide")
                            .queryParam("timezone", "auto")
                            .queryParam("forecast_days", 7)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(WeatherResponse.class)
                    .block();
        }
}