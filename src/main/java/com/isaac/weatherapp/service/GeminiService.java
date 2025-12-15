package com.isaac.weatherapp.service;

import com.isaac.weatherapp.dto.GeminiRequest;

public interface GeminiService {
    String getAiSuggestion(GeminiRequest request);
}
