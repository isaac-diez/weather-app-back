package com.isaac.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForecastDayDTO {

    private String date;
    private double temperatureMax;
    private double temperatureMin;
    private double precipitationProbabilityMax;

}
