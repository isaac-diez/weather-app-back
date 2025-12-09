package com.isaac.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ForecastDTO {

    private String city;
    private List<ForecastDayDTO> days;
}
