package com.isaac.weatherapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class CityResponse {
    private List<CityResult> results;
    private double generationtime_ms;
}
