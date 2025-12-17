package com.isaac.weatherapp.controller;

import com.isaac.weatherapp.dto.*;
import com.isaac.weatherapp.service.CitySearchServiceImpl;
import com.isaac.weatherapp.service.GeminiService;
import com.isaac.weatherapp.service.WeatherServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class WeatherController {

    private final WeatherServiceImpl weatherServiceImpl;
    private final CitySearchServiceImpl citySearchServiceImpl;
    private final GeminiService geminiService;

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

    @PostMapping("/gemini-suggest")
    public ResponseEntity<String> getAiSuggestion(@RequestBody GeminiRequest request) {
        log.info("Solicitud de sugerencia de IA para modo: {} en ubicación: ({}, {})",
                request.getMode(), request.getLatitude(), request.getLongitude());
        try {

            String suggestion = geminiService.getAiSuggestion(request);

            return ResponseEntity.ok(suggestion);
        } catch (RuntimeException e) {

            log.error("Fallo al obtener sugerencia de IA: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado:", e);
            return ResponseEntity.internalServerError().body("Ocurrió un error inesperado en el servidor.");
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("ALIVE");
    }

}
