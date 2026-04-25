package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.AirQualityApiClient;
import com.isaac.weatherapp.dto.*;
import com.isaac.weatherapp.mapping.AirQualityMapper;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AirQualityServiceImpl implements AirQualityService {

    private final AirQualityApiClient airQualityApiClient;
    private final AirQualityMapper airQualityMapper;

    public AirQualityServiceImpl(AirQualityApiClient airQualityApiClient, AirQualityMapper airQualityMapper) {
        this.airQualityApiClient = airQualityApiClient;
        this.airQualityMapper = airQualityMapper;
    }

    public AirQualityDTO getAirQuality(CityDTO city) {

        AirQualityResponse response = airQualityApiClient.getAirQualityData(city.getLatitude(), city.getLongitude());

        AirQualityCurrentDTO current = airQualityMapper.toCurrentDto(response.getCurrent());
        current.setCity(city.getName());
        current.setCityTimeZone(response.getTimezone());

        int startIndex = findCurrentHourIndex(response.getHourly().getTime(), response.getTimezone());
        List<AirQualityHourDTO> hourly = airQualityMapper.toHourlyDtoList(response.getHourly(), startIndex, 24);

        return new AirQualityDTO(current, hourly);
    }

    private int findCurrentHourIndex(List<String> times, String timezone) {
        String nowString = ZonedDateTime.now(ZoneId.of(timezone != null ? timezone : "UTC"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00"));

        int index = times.indexOf(nowString);
        return (index != -1) ? index : 0;
    }

}
