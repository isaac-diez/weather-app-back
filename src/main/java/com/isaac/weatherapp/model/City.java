package com.isaac.weatherapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private String name;
    private double latitude;
    private double longitude;

    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
