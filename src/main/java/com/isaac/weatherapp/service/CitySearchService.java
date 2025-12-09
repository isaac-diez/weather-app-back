package com.isaac.weatherapp.service;

import com.isaac.weatherapp.dto.CityListDTO;

public interface CitySearchService {

    CityListDTO cityList(String cityName);
}
