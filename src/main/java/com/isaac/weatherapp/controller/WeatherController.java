package com.isaac.weatherapp.controller;

import com.isaac.weatherapp.dto.CityDTO;
import com.isaac.weatherapp.dto.CityListDTO;
import com.isaac.weatherapp.dto.CurrentWeatherDTO;
import com.isaac.weatherapp.dto.ForecastDTO;
import com.isaac.weatherapp.service.CitySearchServiceImpl;
import com.isaac.weatherapp.service.WeatherServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin("*")
public class WeatherController {

    private final WeatherServiceImpl weatherServiceImpl;
    private final CitySearchServiceImpl citySearchServiceImpl;

    public WeatherController(WeatherServiceImpl weatherServiceImpl, CitySearchServiceImpl citySearchServiceImpl) {
        this.weatherServiceImpl = weatherServiceImpl;
        this.citySearchServiceImpl = citySearchServiceImpl;
    }

    @PostMapping("/current")
    public CurrentWeatherDTO getCurrentWeather(@RequestBody CityDTO city) {
        return weatherServiceImpl.getCurrentWeather(city);
    }

    @PostMapping("/forecast")
    public ForecastDTO getForecast(@RequestBody CityDTO city) {
        return weatherServiceImpl.getForecast(city);
    }

    @GetMapping("/cities")
    public CityListDTO getCity(@RequestParam String name) {
        return citySearchServiceImpl.cityList(name);
    }

}
