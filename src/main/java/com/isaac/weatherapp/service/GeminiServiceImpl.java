package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.GeminiApiClient;
import com.isaac.weatherapp.dto.CityDTO;
import com.isaac.weatherapp.dto.CurrentWeatherDTO;
import com.isaac.weatherapp.dto.FullWeatherDTO;
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

        FullWeatherDTO weather = weatherService.getFullWeather(cityDto);

        String prompt = buildPrompt(request.getMode(), weather, request.getCity(), request.getLanguage());

        return geminiApiClient.getSuggestion(prompt);
    }

    private String buildPrompt(String mode, FullWeatherDTO weather, String cityName, String languageCode) {
        double temp = weather.getCurrent().getTemperature();
        boolean isRaining = weather.getCurrent().getPrecipitation() > 0.1;
        double humidity = weather.getCurrent().getRelativeHumidity();
        double cloudCover = weather.getCurrent().getCloudCover();

        String safeCityName = cityName != null ? cityName : "the current location";

        String weatherCondition = isRaining ? "It is raining" : "It is not raining";
        String language = getLanguageName(languageCode);
        String promptBase = String.format("I am in %s, current temperature is %.0f°C. " +
                        "Weather: %s, the humidity %.0f%% and the cloud cover is %.0f%%. " +
                        "Use no more than 4 sentences. Do not indicate word count. " +
                        "Do not use markdown. Space the sentences with paragraphs if necessary for better readability. " +
                        "IMPORTANT: You must respond ONLY in %s language.",
                safeCityName, temp, weatherCondition, humidity, cloudCover, language);

        switch (mode) {
            case "outfit":
                return promptBase + "Act as a wise-ass angry gay fashion stylist. Recommend a brief outfit using emojis giving the reason why.";
            case "activity":
                return promptBase + "Act as a completely unhinged hysterical karen type of person local guide. Recommend 2 brief activities right now using emojis.";
            case "laundry":
                return promptBase + "Act as a trust fund preppy nepo baby Home expert. Is it a good time to do laundry and hang clothes outside? Explain why. ";
            case "drink":
                return promptBase + "Act as Ron Swanson if he was a Bar Expert. Recommend an ideal drink for this weather in local bar or cafeteria and suggest drink it at the bar or on the terrace and why.";
            default:
                return "Provide a concise and curious fact about the weather in " + cityName + ".";
        }

    }

    private String getLanguageName(String code) {
        if (code == null ) return "English";
        return switch(code.toLowerCase()) {
            case "es" -> "Spanish";
            case "fr" -> "French";
            case "de" -> "German";
            case "it" -> "Italian";
            case "pt" -> "Portuguese";
            case "ru" -> "Russian";
            case "ja" -> "Japanese";
            case "zh" -> "Chinese";
            case "ko" -> "Korean";
            default -> "English";
        };
    }

}
