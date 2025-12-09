package com.isaac.weatherapp.dto;

import lombok.Data;

@Data
public class CityResult {

    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private String admin1;
}
