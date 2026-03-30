package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.WeatherApiClient;
import com.isaac.weatherapp.dto.*;
import org.slf4j.Logger;
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
        current.setCityTimeZone(response.getTimezone());
        current.setTemperature(safe(response.getCurrent().getTemperature()));
        current.setApparentTemperature(safe(response.getCurrent().getApparentTemperature()));
        current.setRelativeHumidity(safe(response.getCurrent().getRelativeHumidity()));
        current.setCloudCover(safe(response.getCurrent().getCloudCover()));
        current.setIsDay(response.getCurrent().getIsDay());
        current.setPrecipitation(safe(response.getCurrent().getPrecipitation()));
        current.setUvIndex(safe(response.getCurrent().getUvIndex()));
        current.setWindSpeed(safe(response.getCurrent().getWindSpeed()));
        current.setWindDirection(safe(response.getCurrent().getWindDirection()));
        current.setWindGusts(safe(response.getCurrent().getWindGusts()));
        current.setObservationTime(response.getCurrent().getTime());

        ForecastDTO forecast = new ForecastDTO();
        forecast.setCity(city.getName());

        List<ForecastDayDTO> days = new ArrayList<>();

        for (int i = 0; i < response.getDaily().getTime().size(); i++) {
            ForecastDayDTO d = new ForecastDayDTO();
            d.setDate(response.getDaily().getTime().get(i));
            d.setTemperatureMax(safe(response.getDaily().getTemperatureMax().get(i)));
            d.setTemperatureMin(safe(response.getDaily().getTemperatureMin().get(i)));
            d.setPrecipitationProbabilityMax(safe(response.getDaily().getPrecipitationProbabilityMax().get(i)));
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
                    h.setTemperature_2m(safe(response.getHourly().getTemperature_2m().get(i)));
                    h.setRain(safe(response.getHourly().getRain().get(i)));
                    h.setWeather_code(response.getHourly().getWeather_code().get(i));
                    h.setPrecipitation_probability(safe(response.getHourly().getPrecipitation_probability().get(i)));
                    h.setUv_index(safe(response.getHourly().getUvIndex().get(i)));
                    return h;
                })
                .toList();

        forecast.setHours(hours);

        int currentIndex = IntStream.range(0, response.getHourly().getTime().size())
                .filter(i -> response.getHourly().getTime().get(i).equals(nowString))
                .findFirst()
                .orElse(0);

        SolarSummaryDTO solar = new SolarSummaryDTO();

        double uvMax = response.getDaily().getUvIndexMax().getFirst();
        solar.setMaxUvIndexToday(uvMax);
        solar.setSunshineHours(formatSecondsToHHmm(response.getDaily().getSunshineDuration().getFirst()));
        solar.setDaylightHours(formatSecondsToHHmm(response.getDaily().getDaylightDuration().getFirst()));
        solar.setSunrise(response.getDaily().getSunrise().getFirst());
        solar.setSunset(response.getDaily().getSunset().getFirst());

        solar.setUvIndexHourly(safe(response.getHourly().getUvIndex().get(currentIndex)));
        solar.setShortwaveRadiation(safe(response.getHourly().getShortwaveRadiation().get(currentIndex)));

        int peakIndex = 0;
        double maxUvTemp = 0;
        for (int i = currentIndex; i < response.getHourly().getUvIndex().size(); i++) {
            if (response.getHourly().getUvIndex().get(i) > maxUvTemp) {
                maxUvTemp = response.getHourly().getUvIndex().get(i);
                peakIndex = i;
            }
        }
        solar.setPeakUvTime(response.getHourly().getTime().get(peakIndex));

        double uvIndexHourly = response.getHourly().getUvIndex().get(currentIndex);
        if (uvIndexHourly <= 2) {
            solar.setRiskLevel("Bajo");
            solar.setRecommendation("No se requiere protección especial.");
        } else if (uvIndexHourly <= 5) {
            solar.setRiskLevel("Medio");
            solar.setRecommendation("Usa gafas de sol y crema solar si estarás fuera más de 30 min.");
        } else if (uvIndexHourly <= 7) {
            solar.setRiskLevel("Alto");
            solar.setRecommendation("Busca la sombra. Camisa, crema SPF 30+ y sombrero son necesarios.");
        } else {
            solar.setRiskLevel("Extremo");
            solar.setRecommendation("Evita salir en las horas centrales. Riesgo de quemadura muy rápido.");
        }

        String rawSunrise = response.getDaily().getSunrise().getFirst();
        String rawSunset = response.getDaily().getSunset().getFirst();

        ZonedDateTime sunriseDT = java.time.LocalDateTime.parse(rawSunrise).atZone(zoneId);
        ZonedDateTime sunsetDT = java.time.LocalDateTime.parse(rawSunset).atZone(zoneId);

        ZonedDateTime now = ZonedDateTime.now(zoneId);

        if (now.isBefore(sunriseDT)) {
            solar.setDayProgressPercent(0.0);
            solar.setIsNight(true);
        } else if (now.isAfter(sunsetDT)) {
            solar.setDayProgressPercent(100.0);
            solar.setIsNight(true);
        } else {
            long totalMinutesDay = java.time.Duration.between(sunriseDT, sunsetDT).toMinutes();
            long minutesPassed = java.time.Duration.between(sunriseDT, now).toMinutes();

            if (totalMinutesDay > 0) {
                double percent = ((double) minutesPassed / totalMinutesDay) * 100.0;
                solar.setDayProgressPercent(Math.round(percent * 10.0) / 10.0);
                solar.setIsNight(false);
            } else {
                solar.setDayProgressPercent(50.0); // Caso borde (polos)
                solar.setIsNight(false);
            }
        }

        return new FullWeatherDTO(current, forecast, solar);
    }

    private double safe(Double value) {
        return value != null ? value : 0.0;
    }

    private String formatSecondsToHHmm(Double seconds) {
        if (seconds == null) return "00:00";
        long totalMinutes = Math.round(seconds / 60.0);
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;
        return String.format("%02dh:%02dm", hours, minutes);
    }

}
