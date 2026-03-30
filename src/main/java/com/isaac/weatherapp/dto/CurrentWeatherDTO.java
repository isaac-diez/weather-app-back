package com.isaac.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentWeatherDTO {
    private String city;
    private String cityTimeZone;
    private Double temperature;
    private Double relativeHumidity;
    private Double apparentTemperature;
    private Integer isDay;
    private Double precipitation;
    private Double cloudCover;
    private Double uvIndex;
    private Double windSpeed;
    private Double windDirection;
    private Double windGusts;
    private String observationTime;
}


