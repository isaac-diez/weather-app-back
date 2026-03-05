package com.isaac.weatherapp.dto;

import lombok.Data;

@Data
public class ForecastHourDTO {

    private String hour;
    private Double temperature_2m;
    private Integer weather_code;
    private Double rain;
    private Double precipitation_probability;

}
