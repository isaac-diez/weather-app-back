package com.isaac.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForecastDayDTO {

    private String date;
    private Double temperatureMax;
    private Double temperatureMin;
    private Double precipitationProbabilityMax;
    private Double apparentTemperatureMax;
    private Double apparentTemperatureMin;
    private Double uvIndexMax;
    private Double uvIndexClearSkyMax;
    private Double sunshineDuration;
    private Double daylightDuration;
}
