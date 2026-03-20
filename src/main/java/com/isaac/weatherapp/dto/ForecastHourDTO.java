package com.isaac.weatherapp.dto;

import lombok.Data;

@Data
public class ForecastHourDTO {

    private String hour;
    private Double temperature_2m;
    private Integer weather_code;
    private Double rain;
    private Double precipitation_probability;
    private Double cloudCover;
    private Double uvIndex;
    private Double uvIndexClearSky;
    private Double sunshineDuration;
    private Double shortwaveRadiation;
    private Double directRadiation;
    private Double diffuseRadiation;

}
