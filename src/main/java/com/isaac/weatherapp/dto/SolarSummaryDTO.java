package com.isaac.weatherapp.dto;

import lombok.Data;

@Data
public class SolarSummaryDTO {
    private Double maxUvIndexToday;
    private String peakUvTime;
    private Double sunshineHours;
    private Double daylightHours;
    private String riskLevel;
    private String recommendation;
}
