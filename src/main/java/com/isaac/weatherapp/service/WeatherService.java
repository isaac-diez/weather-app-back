package com.isaac.weatherapp.service;

import com.isaac.weatherapp.dto.CityDTO;
import com.isaac.weatherapp.dto.FullWeatherDTO;

public interface WeatherService {

    FullWeatherDTO getFullWeather(CityDTO city);
}
