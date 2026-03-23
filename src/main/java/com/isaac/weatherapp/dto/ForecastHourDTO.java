package com.isaac.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForecastHourDTO {

    private String hour;
    private Double temperature_2m;
    private Integer weather_code;
    private Double rain;
    private Double precipitation_probability;
    private Double cloudCover;
    private Double uv_index;
    private Double uv_index_clear_sky;
    private Double sunshine_duration;
    private Double shortwave_radiation;
    private Double direct_radiation;
    private Double diffuse_radiation;

}
