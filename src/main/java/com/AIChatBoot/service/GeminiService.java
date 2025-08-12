package com.AIChatBoot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private static final Logger log = LoggerFactory.getLogger(GeminiService.class);

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.default.model}")
    private String defaultModel;

    private final RestTemplate restTemplate;

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getGeminiResponseText(String prompt) {
        String url = apiUrl + "/" + defaultModel + ":generateContent?key=" + apiKey;
        log.debug("Gemini API URL: {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Proper request body: contents: [ { parts: [ { text: prompt } ] } ]
        Map<String, Object> textPart = new HashMap<>();
        textPart.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(textPart));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            var response = restTemplate.postForEntity(url, entity, Map.class);
            log.debug("Gemini API Response: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object candidatesObj = response.getBody().get("candidates");
                if (candidatesObj instanceof List<?> candidates && !candidates.isEmpty()) {
                    Object firstCandidate = candidates.get(0);
                    if (firstCandidate instanceof Map<?, ?> candidateMap) {
                        Object contentObj = candidateMap.get("content");
                        if (contentObj instanceof Map<?, ?> contentMap2) {
                            Object partsObj = contentMap2.get("parts");
                            if (partsObj instanceof List<?> parts && !parts.isEmpty()) {
                                Object firstPart = parts.get(0);
                                if (firstPart instanceof Map<?, ?> partMap) {
                                    Object text = partMap.get("text");
                                    if (text instanceof String s) {
                                        return s;
                                    }
                                }
                            }
                        }
                    }
                }
                return "No response text found.";
            } else {
                log.error("Gemini API request failed with status code: {}", response.getStatusCode());
                return "Gemini API request failed.";
            }
        } catch (Exception e) {
            log.error("Error calling Gemini API:", e);
            return "Error calling Gemini API: " + e.getMessage();
        }
    }

    public String generateContent(String prompt) {
        return getGeminiResponseText(prompt);
    }
}