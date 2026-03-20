package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.WeatherApiClient;
import com.isaac.weatherapp.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private WeatherApiClient weatherApiClient;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    private WeatherResponse mockResponse;
    private CityDTO mockCity;

    @BeforeEach
    void setUp() {
        mockCity = new CityDTO();
        mockCity.setName("Barcelona");
        mockCity.setLatitude(41.38);
        mockCity.setLongitude(2.17);

        mockResponse = new WeatherResponse();
        mockResponse.setTimezone("Europe/Madrid");

        WeatherResponse.Current current = new WeatherResponse.Current();
        current.setTime("2026-03-20T12:00");
        current.setTemperature(15.0);
        current.setApparentTemperature(14.0);
        current.setRelativeHumidity(60.0);
        current.setCloudCover(20.0);
        current.setIsDay(1);
        current.setPrecipitation(0.0);
        current.setUvIndex(2.5);
        current.setWindSpeed(10.0);
        current.setWindDirection(180.0);
        current.setWindGusts(15.0);
        mockResponse.setCurrent(current);

        WeatherResponse.Hourly hourly = new WeatherResponse.Hourly();
        String h1 = "2026-03-20T13:00";
        String h2 = "2026-03-20T14:00";

        hourly.setTime(List.of(h1, h2));
        hourly.setTemperature_2m(List.of(18.0, 19.0));
        hourly.setWeather_code(List.of(0, 1));
        hourly.setRain(List.of(0.0, 0.0));
        hourly.setPrecipitation_probability(List.of(0.0, 5.0));
        hourly.setUvIndex(List.of(4.0, 5.5));
        hourly.setCloudCover(List.of(10.0, 15.0));
        hourly.setUvIndexClearSky(List.of(4.0, 5.5));
        hourly.setSunshineDuration(List.of(3600.0, 3600.0));
        hourly.setShortwaveRadiation(List.of(400.0, 600.0));
        hourly.setDirectRadiation(List.of(300.0, 500.0));
        hourly.setDiffuseRadiation(List.of(100.0, 100.0));
        mockResponse.setHourly(hourly);

        WeatherResponse.Daily daily = new WeatherResponse.Daily();
        daily.setTime(List.of("2026-03-20", "2026-03-21"));
        daily.setTemperatureMax(List.of(22.0, 23.0));
        daily.setTemperatureMin(List.of(12.0, 13.0));
        daily.setPrecipitationProbabilityMax(List.of(10.0, 20.0));
        daily.setApparentTemperatureMax(List.of(21.0, 22.0));
        daily.setApparentTemperatureMin(List.of(11.0, 12.0));
        daily.setUvIndexMax(List.of(5.5, 6.0));
        daily.setUvIndexClearSkyMax(List.of(5.5, 6.0));
        daily.setSunshineDuration(List.of(36000.0, 38000.0));
        daily.setDaylightDuration(List.of(43200.0, 43500.0));
        mockResponse.setDaily(daily);
    }

    @Test
    void whenGetFullWeather_thenReturnCompleteDTO() {
        when(weatherApiClient.getWeatherData(anyDouble(), anyDouble())).thenReturn(mockResponse);

        FullWeatherDTO result = weatherService.getFullWeather(mockCity);

        assertNotNull(result);
        assertEquals("Barcelona", result.getCurrent().getCity());
        assertEquals(15.0, result.getCurrent().getTemperature());
    }

    @Test
    void testSolarSummaryLogic_CalculationCorrect() {

        when(weatherApiClient.getWeatherData(anyDouble(), anyDouble())).thenReturn(mockResponse);

        FullWeatherDTO result = weatherService.getFullWeather(mockCity);
        SolarSummaryDTO solar = result.getSolarSummary();

        assertEquals(5.5, solar.getMaxUvIndexToday());
        assertEquals(10.0, solar.getSunshineHours());
        assertEquals("Alto", solar.getRiskLevel());
        assertTrue(solar.getRecommendation().contains("Busca la sombra"));
    }

    @Test
    void testPeakUvTime_MatchesHourlyData() {
        when(weatherApiClient.getWeatherData(anyDouble(), anyDouble())).thenReturn(mockResponse);

        FullWeatherDTO result = weatherService.getFullWeather(mockCity);

        String expectedPeak = "2026-03-20T14:00";
        assertEquals(expectedPeak, result.getSolarSummary().getPeakUvTime());
    }
}