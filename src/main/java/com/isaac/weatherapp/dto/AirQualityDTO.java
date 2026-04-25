package com.isaac.weatherapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class AirQualityDTO {
    
    private AirQualityCurrentDTO current;
    private List<AirQualityHourDTO> hourly;

    public AirQualityDTO(AirQualityCurrentDTO current, List<AirQualityHourDTO> hourly) {
        this.current = current;
        this.hourly = hourly;
    }

    public AirQualityDTO() {

    }
}
