package com.cisco.courseservice.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class OllamaService {


    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final RestTemplate restTemplate;

    public String generateDescription(String title, String language) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama3.2"); // or whichever model you've pulled
        requestBody.put("prompt", "Write a short 2-3 sentence course description for a course titled '"
                + title + "' about " + language + ". Be concise and clear.");
        requestBody.put("stream", false);

        Map<String, Object> response = restTemplate.postForObject(OLLAMA_URL, requestBody, Map.class);

        if (response != null && response.get("response") != null) {
            return response.get("response").toString().trim();
        }
        return "Description unavailable";
    }
}

