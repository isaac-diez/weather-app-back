package com.isaac.weatherapp.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Component
@Slf4j
public class GeminiApiClient {

    private final String apiKey;
    private final WebClient geminiWebClient;
    public final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiApiClient(
            WebClient.Builder webClientBuilder,
            @Value("${api.gemini.key}") String apiKey) {

        this.apiKey = apiKey != null ? apiKey.trim() : "";

        this.geminiWebClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/")
                .build();
    }

    @Retryable(
            retryFor = { WebClientResponseException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public String getSuggestion(String prompt) {

        if (this.apiKey.isEmpty()) {
            log.error("API key is missing or empty.");
            throw new RuntimeException("Error: API key configuration is missing or empty. Check your application properties.");
        }


        String modelName = "gemini-2.5-flash";

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

        log.info("Calling Gemini API..." + prompt);

        try {
            String responseBody = geminiWebClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path(modelName + ":generateContent")
                            .queryParam("key", this.apiKey)
                            .build())
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(60))
                    .block();

            log.info("Gemini API responded successfully.");
            return extractTextFromGeminiResponse(responseBody);
        } catch (Exception e) {
            log.error("Error calling Gemini API: {}", e.getMessage());
            throw new RuntimeException("Error: Connection with the AI service unavailable", e);
        }
    }

    private String extractTextFromGeminiResponse(String response) {

        try {
            String extractedText = objectMapper.readTree(response)
                    .path("candidates").path(0)
                    .path("content").path("parts").path(0)
                    .path("text")
                    .asText("Empty AI response.");

            if (!"Empty AI response.".equals(extractedText)) {
                log.info("Text successfully extracted from Gemini response ({} characters)", extractedText.length());
            } else {
                log.warn("Gemini returned a valid JSON but no text was found in the expected path.");
            }

            return extractedText;
        } catch (Exception e) {
            log.error("Error processing AI response: {}", e.getMessage());
            return "Error processing Gemini response";
        }
    }

}
