package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.WeatherApiClient;
import com.isaac.weatherapp.dto.*;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherApiClient weatherApiClient;

    public WeatherServiceImpl(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    public FullWeatherDTO getFullWeather(CityDTO city) {

        WeatherResponse response = weatherApiClient.getWeatherData(city.getLatitude(), city.getLongitude());

        CurrentWeatherDTO current = new CurrentWeatherDTO();
        current.setCity(city.getName());
        current.setTemperature(response.getCurrent().getTemperature());
        current.setApparentTemperature(response.getCurrent().getApparentTemperature());
        current.setRelativeHumidity(response.getCurrent().getRelativeHumidity());
        current.setCloudCover(response.getCurrent().getCloudCover());
        current.setIsDay(response.getCurrent().getIsDay());
        current.setPrecipitation(response.getCurrent().getPrecipitation());
        current.setUvIndex(response.getCurrent().getUvIndex());
        current.setWindSpeed(response.getCurrent().getWindSpeed());
        current.setWindDirection(response.getCurrent().getWindDirection());
        current.setWindGusts(response.getCurrent().getWindGusts());
        current.setObservationTime(response.getCurrent().getTime());

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

        String cityTimeZone = (response.getTimezone() != null) ? response.getTimezone() : "UTC";
        ZoneId zoneId = ZoneId.of(cityTimeZone);

        ZonedDateTime nowInCity = ZonedDateTime.now(zoneId);
        String nowString = nowInCity.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00"));

        List<ForecastHourDTO> hours = IntStream.range(0, response.getHourly().getTime().size())
                .filter(i -> {
                    String hourTime = response.getHourly().getTime().get(i);
                    return hourTime.compareTo(nowString) >= 0;
                })
                .limit(24)
                .mapToObj(i -> {
                    ForecastHourDTO h = new ForecastHourDTO();
                    h.setHour(response.getHourly().getTime().get(i));
                    h.setTemperature_2m(response.getHourly().getTemperature_2m().get(i));
                    h.setRain(response.getHourly().getRain().get(i));
                    h.setWeather_code(response.getHourly().getWeather_code().get(i));
                    h.setPrecipitation_probability(response.getHourly().getPrecipitation_probability().get(i));
                    return h;
                })
                .toList();

        forecast.setHours(hours);

        return new FullWeatherDTO(current, forecast);
    }


}
