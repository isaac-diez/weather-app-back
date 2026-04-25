package com.isaac.weatherapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirQualityHourDTO {
    private String hour;
    private Double pm10;
    private Double pm2_5;
    private Double carbonMonoxide;
    private Double carbonDioxide;
    private Double nitrogenDioxide;
    private Double sulphurDioxide;
    private Double ozone;
    private Double aerosolOpticalDepth;
    private Double dust;
    private Double ammonia;
    private Double methane;
    private Double ragweedPollen;
    private Double olivePollen;
    private Double mugwortPollen;
    private Double grassPollen;
    private Double birchPollen;
    private Double alderPollen;
    private Double europeanAqi;
    private Double europeanAqiPm2_5;
    private Double europeanAqiPm10;
    private Double europeanAqiOzone;
    private Double europeanAqiNitrogenDioxide;
    private Double europeanAqiSulphurDioxide;
    private Double formaldehyde;
    private Double glyoxal;
    private Double nonMethaneVolatileOrganicCompounds;
    private Double peroxyacylNitrates;
    private Double secondaryInorganicAerosol;
    private Double residentialElementaryCarbon;
    private Double totalElementaryCarbon;
    private Double pm2_5TotalOrganicMatter;
    private Double seaSaltAerosol;
    private Double nitrogenMonoxide;

}
