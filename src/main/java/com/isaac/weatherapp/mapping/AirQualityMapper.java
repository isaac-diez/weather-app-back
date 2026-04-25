package com.isaac.weatherapp.mapping;

import com.isaac.weatherapp.dto.AirQualityCurrentDTO;
import com.isaac.weatherapp.dto.AirQualityHourDTO;
import com.isaac.weatherapp.dto.AirQualityResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface AirQualityMapper {

    AirQualityCurrentDTO toCurrentDto(AirQualityResponse.Current source);

    default List<AirQualityHourDTO> toHourlyDtoList(AirQualityResponse.Hourly hourly, int startIndex, int limit) {
        return IntStream.range(startIndex, startIndex + limit)
                .mapToObj(i -> {
                    AirQualityHourDTO dto = new AirQualityHourDTO();
                    dto.setHour(hourly.getTime().get(i));
                    dto.setPm10(hourly.getPm10().get(i));
                    dto.setPm2_5(hourly.getPm2_5().get(i));
                    dto.setEuropeanAqi(hourly.getEuropean_aqi().get(i));
                    dto.setOzone(hourly.getOzone().get(i));
                    dto.setNitrogenDioxide(hourly.getNitrogenDioxide().get(i));
                    dto.setBirchPollen(hourly.getBirchPollen().get(i));
                    dto.setAlderPollen(hourly.getAlderPollen().get(i));
                    dto.setGrassPollen(hourly.getGrassPollen().get(i));
                    dto.setOlivePollen(hourly.getOlivePollen().get(i));
                    dto.setRagweedPollen(hourly.getRagweedPollen().get(i));
                    dto.setMugwortPollen(hourly.getMugwortPollen().get(i));
                    dto.setAerosolOpticalDepth(hourly.getAerosolOpticalDepth().get(i));
                    dto.setDust(hourly.getDust().get(i));
                    return dto;
                })
                .toList();
    }
}
