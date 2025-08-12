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

        Map<String, Object> content = new HashMap<>();
        content.put("parts", new HashMap<String, String>() {{
            put("text", prompt);
        }});

        Map<String, Object> data = new HashMap<>();
        data.put("contents", new HashMap[]{(HashMap) content});

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            log.debug("Gemini API Response: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    Map<String, Object> contentMap = (Map<String, Object>) candidate.get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) contentMap.get("parts");
                    if (parts != null && !parts.isEmpty()) {
                        Map<String, Object> part = parts.get(0);
                        return (String) part.get("text");
                    }
                }
                return "âŒ No response text found.";
            } else {
                log.error("Gemini API request failed with status code: {}", response.getStatusCode());
                return "âŒ Gemini API request failed.";
            }
        } catch (Exception e) {
            log.error("Error calling Gemini API:", e);
            return "âŒ Error calling Gemini API: " + e.getMessage();
        }
    }


    public String generateContent(String prompt) {
        return getGeminiResponseText(prompt);  // Reuse getGeminiResponseText
    }
}