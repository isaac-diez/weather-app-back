package com.isaac.weatherapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class ForecastHourDTO {

    private String date;
    private List<Double> temperature_2m;
    private List<Double> relative_humidity_2m;
    private List<Double> apparent_temperature;
    private List<Double> precipitation_probability;

}
