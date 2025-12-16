package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.GeminiApiClient;
import com.isaac.weatherapp.dto.CityDTO;
import com.isaac.weatherapp.dto.CurrentWeatherDTO;
import com.isaac.weatherapp.dto.GeminiRequest;
import org.springframework.stereotype.Service;

@Service
public class GeminiServiceImpl implements GeminiService {

    private final GeminiApiClient geminiApiClient;
    private final WeatherService weatherService;

    public GeminiServiceImpl(GeminiApiClient geminiApiClient, WeatherService weatherService) {
        this.geminiApiClient = geminiApiClient;
        this.weatherService = weatherService;
    }

    public String getAiSuggestion(GeminiRequest request) {

        CityDTO cityDto = new CityDTO();
        cityDto.setName(request.getCity());
        cityDto.setLatitude(request.getLatitude());
        cityDto.setLongitude(request.getLongitude());

        CurrentWeatherDTO weather = weatherService.getCurrentWeather(cityDto);

        String prompt = buildPrompt(request.getMode(), weather, request.getCity());

        return geminiApiClient.getSuggestion(prompt);
    }

    private String buildPrompt(String mode, CurrentWeatherDTO weather, String cityName) {
        double temp = weather.getTemperature();
        boolean isRaining = weather.getPrecipitation() > 0.1;
        double humidity = weather.getRelativeHumidity();
        double cloudCover = weather.getCloudCover();

        String safeCityName = cityName != null ? cityName : "the current location";

        String weatherCondition = isRaining ? "It is raining" : "It is not raining";

        String promptBase = String.format("I am in %s, current temperature is %.0f°C. Weather: %s, the humidity %.0f%% and the cloud cover is %.0f%%.  ",
                safeCityName, temp, weatherCondition, humidity, cloudCover);

        switch (mode) {
            case "outfit":
                return promptBase + "Act as a wise-ass angry gay fashion stylist. Recommend a brief outfit using emojis giving the reason why in no more than 2 paragraphs. Do not indicate word count. Do not use markdown";
            case "activity":
                return promptBase + "Act as a completely unhinged hysterical karen type of person local guide. Recommend 2 brief activities right now using emojis. If you can't find anything interesting, suggest a plan for the nearest major city. Use no more than 2 paragraphs. Do not indicate word count. Do not use markdown";
            case "laundry":
                return promptBase + "Act as a trust fund preppy nepo baby Home expert. Is it a good time to do laundry and hang clothes outside? Explain why in in no more than 2 paragraphs and witty sentences. Do not indicate word count. Do not use markdown";
            case "drink":
                return promptBase + "Act as Ron Swanson if he was a Bar Expert. Recommend an ideal drink for this weather in local bar or cafeteria and suggest drink it at the bar or on the terrace in no more than 4 sentences. Do not indicate word count. Do not use markdown";
            default:
                return "Provide a concise and curious fact about the weather in " + cityName + ".";
        }

    }

}
