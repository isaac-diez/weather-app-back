package com.isaac.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentWeatherDTO {
    private String city;
    private double temperature;
    private double relativeHumidity;
    private double apparentTemperature;
    private double isDay;
    private double precipitation;
    private double cloudCover;
    private double uvIndex;
    private double windSpeed;
    private double windDirection;
    private double windGusts;
    private String observationTime;
}


