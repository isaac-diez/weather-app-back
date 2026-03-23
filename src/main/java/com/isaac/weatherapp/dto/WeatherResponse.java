package com.isaac.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private double latitude;
    private double longitude;

    private String timezone;
    private Current current;
    private Daily daily;
    private Hourly hourly;

    @Data
    public static class Current {

        private String time;

        @JsonProperty("temperature_2m") private Double temperature;
        @JsonProperty("relative_humidity_2m") private Double relativeHumidity;
        @JsonProperty("apparent_temperature") private Double apparentTemperature;
        @JsonProperty("is_day") private Integer isDay;
        @JsonProperty("precipitation") private Double precipitation;
        @JsonProperty("cloud_cover") private Double cloudCover;
        @JsonProperty("uv_index") private Double uvIndex;
        @JsonProperty("wind_speed_10m") private Double windSpeed;
        @JsonProperty("wind_direction_10m") private Double windDirection;
        @JsonProperty("wind_gusts_10m") private Double windGusts;
        }

    @Data
    public static class Daily {

        private List<String> time;

        @JsonProperty("temperature_2m_max") private List<Double> temperatureMax;
        @JsonProperty("temperature_2m_min") private List<Double> temperatureMin;
        @JsonProperty("precipitation_probability_max") private List<Double> precipitationProbabilityMax;
        @JsonProperty("apparent_temperature_max") private List<Double> apparentTemperatureMax;
        @JsonProperty("apparent_temperature_min") private List<Double> apparentTemperatureMin;
        @JsonProperty("uv_index_max") private List<Double> uvIndexMax;
        @JsonProperty("uv_index_clear_sky_max") private List<Double> uvIndexClearSkyMax;
        @JsonProperty("sunshine_duration") private List<Double> sunshineDuration;
        @JsonProperty("daylight_duration") private List<Double> daylightDuration;

    }

    @Data
    public static class Hourly {

        private List<String> time;

        @JsonProperty("temperature_2m") private List<Double> temperature_2m;
        @JsonProperty("weather_code") private List<Integer> weather_code;
        @JsonProperty("rain") private List<Double> rain;
        @JsonProperty("precipitation_probability") private List<Double> precipitation_probability;
        @JsonProperty("cloud_cover") private List<Double> cloudCover;
        @JsonProperty("uv_index") private List<Double> uvIndex;
        @JsonProperty("uv_index_clear_sky") private List<Double> uvIndexClearSky;
        @JsonProperty("sunshine_duration") private List<Double> sunshineDuration;
        @JsonProperty("shortwave_radiation") private List<Double> shortwaveRadiation;
        @JsonProperty("direct_radiation") private List<Double> directRadiation;
        @JsonProperty("diffuse_radiation") private List<Double> diffuseRadiation;
    }
}
