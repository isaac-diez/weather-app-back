package com.isaac.weatherapp.dto;

import lombok.Data;

@Data
public class CityDTO {

    private String name;
    private String country;
    private double latitude;
    private double longitude;
    private String region;

    public CityDTO mapToDTO(CityResult result) {

        CityDTO dto = new CityDTO();
        dto.setName(result.getName());
        dto.setCountry(result.getCountry());
        dto.setLatitude(result.getLatitude());
        dto.setLongitude(result.getLongitude());
        dto.setRegion(result.getAdmin1());
        return dto;
    }
}
