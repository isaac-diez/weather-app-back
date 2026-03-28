package com.isaac.weatherapp.dto;

import lombok.Data;

@Data
public class SolarSummaryDTO {
    private Double uvIndexHourly;
    private Double maxUvIndexToday;
    private String peakUvTime;
    private String sunshineHours;
    private String daylightHours;
    private String riskLevel;
    private String recommendation;
    private String sunrise;
    private String sunset;
    private Double shortwaveRadiation;
    private Double dayProgressPercent;
    private Boolean isNight;
}
