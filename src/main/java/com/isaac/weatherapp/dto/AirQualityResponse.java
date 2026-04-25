package com.isaac.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQualityResponse {

    private double latitude;
    private double longitude;

    private String timezone;
    private Current current;
    private Hourly hourly;

    @Data
    public static class Current {

        private String time;

        @JsonProperty("european_aqi") private Double europeanAqi;
        @JsonProperty("pm2_5") private Double pm2_5;
        @JsonProperty("pm10") private Double pm10;
        @JsonProperty("carbon_monoxide") private Double carbonMonoxide;
        @JsonProperty("nitrogen_dioxide") private Double nitrogenDioxide;
        @JsonProperty("sulphur_dioxide") private Double sulphurDioxide;
        @JsonProperty("ozone") private Double ozone;
        @JsonProperty("aerosol_optical_depth") private Double aerosolOpticalDepth;
        @JsonProperty("dust") private Double dust;
        @JsonProperty("ammonia") private Double ammonia;
        @JsonProperty("ragweed_pollen") private Double ragweedPollen;
        @JsonProperty("olive_pollen") private Double olivePollen;
        @JsonProperty("mugwort_pollen") private Double mugwortPollen;
        @JsonProperty("grass_pollen") private Double grassPollen;
        @JsonProperty("birch_pollen") private Double birchPollen;
        @JsonProperty("alder_pollen") private Double alderPollen;
        }


    @Data
    public static class Hourly {

        private List<String> time;

        @JsonProperty("pm10") private List<Double> pm10;
        @JsonProperty("pm2_5") private List<Double> pm2_5;
        @JsonProperty("rain") private List<Double> rain;
        @JsonProperty("carbon_monoxide") private List<Double> carbonMonoxide;
        @JsonProperty("carbon_dioxide") private List<Double> carbonDioxide;
        @JsonProperty("nitrogen_dioxide") private List<Double> nitrogenDioxide;
        @JsonProperty("sulphur_dioxide") private List<Double> sulphurDioxide;
        @JsonProperty("ozone") private List<Double> ozone;
        @JsonProperty("aerosol_optical_depth") private List<Double> aerosolOpticalDepth;
        @JsonProperty("dust") private List<Double> dust;
        @JsonProperty("ammonia") private List<Double> ammonia;
        @JsonProperty("methane") private List<Double> methane;
        @JsonProperty("ragweed_pollen") private List<Double> ragweedPollen;
        @JsonProperty("olive_pollen") private List<Double> olivePollen;
        @JsonProperty("mugwort_pollen") private List<Double> mugwortPollen;
        @JsonProperty("grass_pollen") private List<Double> grassPollen;
        @JsonProperty("birch_pollen") private List<Double> birchPollen;
        @JsonProperty("alder_pollen") private List<Double> alderPollen;
        @JsonProperty("european_aqi") private List<Double> european_aqi;
        @JsonProperty("european_aqi_pm2_5") private List<Double> europeanAqiPm2_5;
        @JsonProperty("european_aqi_pm10") private List<Double> europeanAqiPm10;
        @JsonProperty("european_aqi_ozone") private List<Double> europeanAqiOzone;
        @JsonProperty("european_aqi_nitrogen_dioxide") private List<Double> europeanAqiNitrogenDioxide;
        @JsonProperty("european_aqi_sulphur_dioxide") private List<Double> europeanAqiSulphurDioxide;
        @JsonProperty("formaldehyde") private List<Double> formaldehyde;
        @JsonProperty("glyoxal") private List<Double> glyoxal;
        @JsonProperty("non_methane_volatile_organic_compounds") private List<Double> nonMethaneVolatileOrganicCompounds;
        @JsonProperty("peroxyacyl_nitrates") private List<Double> peroxyacylNitrates;
        @JsonProperty("secondary_inorganic_aerosol") private List<Double> secondaryInorganicAerosol;
        @JsonProperty("residential_elementary_carbon") private List<Double> residentialElementaryCarbon;
        @JsonProperty("total_elementary_carbon") private List<Double> totalElementaryCarbon;
        @JsonProperty("pm2_5_total_organic_matter") private List<Double> pm2_5TotalOrganicMatter;
        @JsonProperty("sea_salt_aerosol") private List<Double> seaSaltAerosol;
        @JsonProperty("nitrogen_monoxide") private List<Double> nitrogenMonoxide;
    }
}
