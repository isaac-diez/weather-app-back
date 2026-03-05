package com.isaac.weatherapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class ForecastHourDTO {

    private String date;
    private List<Double> temperature_2m;
    private List<Double> weather_code;
    private List<Double> rain;
    private List<Double> precipitation_probability;

}
