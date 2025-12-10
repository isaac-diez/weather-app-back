package com.isaac.weatherapp.service;

import com.isaac.weatherapp.client.GeocodingApiClient;
import com.isaac.weatherapp.dto.CityDTO;
import com.isaac.weatherapp.dto.CityListDTO;
import com.isaac.weatherapp.dto.CityResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CitySearchServiceImpl implements CitySearchService {

    private final GeocodingApiClient geocodingApiClient;

    public CitySearchServiceImpl(GeocodingApiClient geocodingApiClient) {
        this.geocodingApiClient = geocodingApiClient;
    }

    public CityListDTO cityList(String cityName) {

        CityResponse response = geocodingApiClient.getCityData(cityName);

        if (response.getResults() == null || response.getResults().isEmpty()) {
            CityListDTO emptyListDTO = new CityListDTO();
            emptyListDTO.setCities(Collections.emptyList());
            return emptyListDTO;
        }

        List<CityDTO> cityDTOs = response.getResults().stream()
                .map(r -> {
                    CityDTO dto = new CityDTO();
                    dto.setName(r.getName());
                    dto.setCountry(r.getCountry());
                    dto.setRegion(r.getAdmin1());
                    dto.setLatitude(r.getLatitude());
                    dto.setLongitude(r.getLongitude());
                    return dto;
                })
                .toList();

        CityListDTO listDTO = new CityListDTO();
        listDTO.setCities(cityDTOs);
        return listDTO;
    }

}
