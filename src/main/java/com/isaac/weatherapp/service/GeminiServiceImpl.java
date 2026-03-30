package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.GeminiApiClient;
import com.isaac.weatherapp.dto.CityDTO;
import com.isaac.weatherapp.dto.FullWeatherDTO;
import com.isaac.weatherapp.dto.GeminiRequest;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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
        double uvIndex = weather.getCurrent().getUvIndex();
        double uvIndexMax = weather.getSolarSummary().getMaxUvIndexToday();
        String uvIndexMaxTime = weather.getSolarSummary().getPeakUvTime();

        String cityTimeZone = (weather.getCurrent().getCityTimeZone() != null) ? weather.getCurrent().getCityTimeZone() : "UTC";
        ZoneId zoneId = ZoneId.of(cityTimeZone);

        ZonedDateTime nowInCity = ZonedDateTime.now(zoneId);

        String safeCityName = cityName != null ? cityName : "the current location";

        String weatherCondition = isRaining ? "It is raining" : "It is not raining";
        String language = getLanguageName(languageCode);
        String promptBase = String.format(" in %s, at %s, current temperature is %.0f°C. " +
                        "Weather: temperature %s, the humidity %.0f%%, the cloud cover is %.0f%% and UV Index %s with a maximum of %s at %s" +
                        "Use no more than 4 sentences. Do not indicate word count. " +
                        "Do not use markdown. Space the sentences with paragraphs for better readability. " +
                        "IMPORTANT: You must respond ONLY in %s language.",
                safeCityName, nowInCity, temp, weatherCondition, humidity, cloudCover, uvIndex, uvIndexMax, uvIndexMaxTime, language);

        switch (mode) {
            case "outfit":
                return  "Act as a wise-ass angry gay fashion stylist " + promptBase + " Recommend a brief outfit using emojis giving the reason why.";
            case "activity":
                return "Act as a completely unhinged hysterical karen type of person local guide. Recommend at least 3 activities or tourist attractions to visit right now (you can use emojis) " + promptBase;
            case "laundry":
                return "Act as a trust fund preppy nepo baby Home expert " + promptBase + "Is it a good time to do laundry and hang clothes outside? Explain why.";
            case "drink":
                return "Act as Ron Swanson if he was a Bar Expert. Recommend an ideal drink for this weather in local bar or cafeteria and suggest where to drink it and why "  + promptBase;
            case "sun":
                return "Act as an expert skin protection professional but don't mention it. Give advice for best hours for outdoor activities minimizing the risks of the direct exposure to the sun " + promptBase + ". And always add to seek expert advice from dermatologists.";
            case "energy":
                return "Act as an expert solar energy engineer. Give expert comments whether today is a good day for solar energy production, max solar production and suggestions to improve it "  + promptBase;
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
