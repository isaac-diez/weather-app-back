package com.isaac.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FullWeatherDTO {
    private CurrentWeatherDTO current;
    private ForecastDTO forecast;
    private SolarSummaryDTO solarSummary;
}
