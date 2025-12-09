package com.isaac.weatherapp.service;

import com.isaac.weatherapp.dto.CityDTO;
import com.isaac.weatherapp.dto.CurrentWeatherDTO;
import com.isaac.weatherapp.dto.ForecastDTO;

public interface WeatherService {

    CurrentWeatherDTO getCurrentWeather(CityDTO city);
    ForecastDTO getForecast(CityDTO city);
}
