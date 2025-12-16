package com.isaac.weatherapp.dto;

import lombok.Data;

@Data
public class GeminiRequest {

    private String mode;
    private String city;
    private double latitude;
    private double longitude;
}
