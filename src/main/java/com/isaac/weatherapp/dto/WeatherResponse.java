package com.isaac.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {

    private double latitude;
    private double longitude;

    private Current current;
    private Daily daily;
    private Hourly hourly;

    @Data
    public static class Current {

        private String time;

        @JsonProperty("temperature_2m")
        private Double temperature;

        @JsonProperty("relative_humidity_2m")
        private Double relativeHumidity;

        @JsonProperty("apparent_temperature")
        private Double apparentTemperature;

        @JsonProperty("is_day")
        private Integer isDay;

        private Double precipitation;

        @JsonProperty("cloud_cover")
        private Double cloudCover;

        @JsonProperty("uv_index")
        private Double uvIndex;

        @JsonProperty("wind_speed_10m")
        private Double windSpeed;

        @JsonProperty("wind_direction_10m")
        private Double windDirection;

        @JsonProperty("wind_gusts_10m")
        private Double windGusts;

    }

    @Data
    public static class Daily {

        private List<String> time;

        @JsonProperty("apparent_temperature_max")
        private List<Double> temperatureMax;

        @JsonProperty("apparent_temperature_min")
        private List<Double> temperatureMin;

        @JsonProperty("precipitation_probability_max")
        private List<Double> precipitationProbabilityMax;

    }

    @Data
    public static class Hourly {
        private List<String> time;

        @JsonProperty("temperature_2m")
        private List<Double> temperature_2m;

        @JsonProperty("relative_humidity_2m")
        private List<Double> relative_humidity_2m;

        @JsonProperty("apparent_temperature")
        private List<Double> apparent_temperature;

        @JsonProperty("precipitation_probability")
        private List<Double> precipitation_probability;
    }
}
