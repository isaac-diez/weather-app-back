package com.isaac.weatherapp.service;

import com.isaac.weatherapp.dto.AirQualityDTO;
import com.isaac.weatherapp.dto.CityDTO;
import com.isaac.weatherapp.dto.FullWeatherDTO;

public interface AirQualityService {
    AirQualityDTO getAirQuality(CityDTO city);

}
