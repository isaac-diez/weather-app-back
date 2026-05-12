package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.AirQualityApiClient;
import com.isaac.weatherapp.dto.*;
import com.isaac.weatherapp.mapping.AirQualityMapper;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        if (times == null || times.isEmpty()) return 0;

        // 1. Detectar la zona horaria real del usuario para obtener su hora actual
        String cityTimeZone = (timezone != null) ? timezone : "UTC";
        ZoneId userZone = ZoneId.of(cityTimeZone);
        ZonedDateTime nowInCity = ZonedDateTime.now(userZone).truncatedTo(java.time.temporal.ChronoUnit.HOURS);

        // 2. Determinar la zona horaria en la que responde la API (GMT/UTC o la local)
        ZoneId apiZone = "GMT".equals(timezone) ? java.time.ZoneOffset.UTC : userZone;

        for (int i = 0; i < times.size(); i++) {
            try {
                // 3. Parseamos el tiempo de la API y le aplicamos la zona en la que viene (apiZone)
                java.time.LocalDateTime ldt = java.time.LocalDateTime.parse(times.get(i));
                ZonedDateTime hourTime = ldt.atZone(apiZone);

                // 4. Comparamos los dos instantes reales en el tiempo
                if (hourTime.isEqual(nowInCity)) {
                    return i;
                }
            } catch (Exception e) {
                // Fallback por si el string de la API ya viene con un offset o formato ISO estricto
                try {
                    ZonedDateTime hourTime = ZonedDateTime.parse(times.get(i));
                    if (hourTime.isEqual(nowInCity)) {
                        return i;
                    }
                } catch (Exception ignored) {}
            }
        }
        return 0; // Fallback si no encuentra coincidencia exacta
    }

}
