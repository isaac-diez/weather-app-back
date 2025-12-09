package com.isaac.weatherapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class CityListDTO {
    private List<CityDTO> cities;
}
