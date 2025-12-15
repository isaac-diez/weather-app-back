package com.isaac.weatherapp.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
@Slf4j
public class GeminiApiClient {

    private final String apiKey;
    private final WebClient geminiWebClient;
    public final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiApiClient(
            WebClient.Builder webClientBuilder,
            @Value("${GEMINI_API_KEY}") String apiKey) {

        this.apiKey = apiKey != null ? apiKey.trim() : "";

        this.geminiWebClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/")
                .build();
    }

    public String getSuggestion(String prompt) {

        // **DEBUGGING:** Registrar la longitud de la clave para verificar que se cargó.
        log.info("API Key Length: {}", this.apiKey.length());

        if (this.apiKey.isEmpty()) {
            // Lanzar un error claro si la clave no se cargó o está vacía.
            log.error("GEMINI_API_KEY is missing or empty.");
            throw new RuntimeException("Error: GEMINI_API_KEY configuration is missing or empty. Check your application properties.");
        }
        log.info("Calling Gemini API...");

        String modelName = "gemini-2.5-flash-preview-09-2025";

        String payload = String.format("""
            {
                "contents": [
                    {
                        "parts": [
                            { "text": "%s" }
                        ]
                    }
                ]
            }
            """, prompt.replace("\"", "\\\""));

        try {
            String responseBody = geminiWebClient.post()
                    .uri(uriBuilder -> uriBuilder
                            // El path relativo al baseUrl, incluyendo el método de la API
                            .pathSegment(modelName + ":generateContent")
                            // Parámetro de consulta para la API Key
                            .queryParam("key", this.apiKey)
                            .build())
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(15))
                    .block();

            return extractTextFromGeminiResponse(responseBody);
        } catch (Exception e) {
            log.error("Error calling Gemini API: {}", e.getMessage());
            throw new RuntimeException("Error: Connection with the AI service unavailable", e);
        }
    }

    private String extractTextFromGeminiResponse(String response) {

        try {
            return objectMapper.readTree(response)
                    .path("candidates").path(0)
                    .path("content").path("parts").path(0)
                    .path("text")
                    .asText("Empty AI response.");
        } catch (Exception e) {
            log.error("Error processing Geminai response: {}", e.getMessage());
            return "Error processing Gemini response";
        }
    }

}
