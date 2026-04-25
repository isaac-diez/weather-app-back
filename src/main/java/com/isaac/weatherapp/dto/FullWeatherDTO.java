package com.isaac.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class FullWeatherDTO {
    private CurrentWeatherDTO current;
    private ForecastDTO forecast;
    private SolarSummaryDTO solar;
    private AirQualityDTO airQuality;

    public FullWeatherDTO(CurrentWeatherDTO current, ForecastDTO forecast, SolarSummaryDTO solar) {
        this.current = current;
        this.forecast = forecast;
        this.solar = solar;
    }

    public void setAirQuality(AirQualityDTO airQuality) {
        this.airQuality = airQuality;
    }
}
