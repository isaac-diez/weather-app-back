package com.isaac.weatherapp.dto;

import lombok.Data;

@Data
public class AirQualityCurrentDTO {
    private String city;
    private String cityTimeZone;
    private Double europeanAqi;
    private Double pm2_5;
    private Double pm10;
    private Double carbonMonoxide;
    private Double nitrogenDioxide;
    private Double sulphurDioxide;
    private Double ozone;
    private Double aerosolOpticalDepth;
    private Double dust;
    private Double ammonia;
    private Double ragweedPollen;
    private Double olivePollen;
    private Double mugwortPollen;
    private Double grassPollen;
    private Double birchPollen;
    private Double alderPollen;
}


