package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.WeatherApiClient;
import com.isaac.weatherapp.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherApiClient weatherApiClient;

    public WeatherServiceImpl(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    public CurrentWeatherDTO getCurrentWeather(CityDTO city) {

        WeatherResponse response = weatherApiClient.getWeatherData(city.getLatitude(), city.getLongitude());

        CurrentWeatherDTO dto = new CurrentWeatherDTO();
        dto.setCity(city.getName());
        dto.setTemperature(response.getCurrent().getTemperature());
        dto.setApparentTemperature(response.getCurrent().getApparentTemperature());
        dto.setRelativeHumidity(response.getCurrent().getApparentTemperature());
        dto.setCloudCover(response.getCurrent().getCloudCover());
        dto.setIsDay(response.getCurrent().getIsDay());
        dto.setPrecipitation(response.getCurrent().getPrecipitation());
        dto.setUvIndex(response.getCurrent().getUvIndex());
        dto.setWindSpeed(response.getCurrent().getWindSpeed());
        dto.setWindDirection(response.getCurrent().getWindDirection());
        dto.setWindGusts(response.getCurrent().getWindGusts());
        dto.setObservationTime(response.getCurrent().getTime());

        return dto;
    }

    public ForecastDTO getForecast(CityDTO city) {

        WeatherResponse response = weatherApiClient.getWeatherData(city.getLatitude(), city.getLongitude());

        ForecastDTO forecast = new ForecastDTO();
        forecast.setCity(city.getName());

        List<ForecastDayDTO> days = new ArrayList<>();

        for (int i = 0; i < response.getDaily().getTime().size(); i++) {
            ForecastDayDTO d = new ForecastDayDTO();
            d.setDate(response.getDaily().getTime().get(i));
            d.setTemperatureMax(response.getDaily().getTemperatureMax().get(i));
            d.setTemperatureMin(response.getDaily().getTemperatureMin().get(i));
            d.setPrecipitationProbabilityMax(response.getDaily().getPrecipitationProbabilityMax().get(i));
            days.add(d);
        }
        forecast.setDays(days);

        return forecast;
    }
}
