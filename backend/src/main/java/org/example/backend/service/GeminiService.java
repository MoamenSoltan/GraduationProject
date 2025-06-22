package org.example.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.dto.AIDTO.GeminiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
public class GeminiService {
    private final WebClient webClient;
    private final String apiKey;

    public GeminiService(WebClient.Builder builder,
                         @Value("${gemini.api.url}") String apiUrl,
                         @Value("${gemini.api.key}") String apiKey) {
        this.webClient = builder.baseUrl(apiUrl).build();
        this.apiKey = apiKey;
    }

    public GeminiResponseDTO askGeminiWithImage(MultipartFile image, String prompt) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(image.getBytes());
        Map<String, Object> inlineData = Map.of(
                "mime_type", image.getContentType(),
                "data", base64
        );
        Map<String, Object> partText = Map.of(
                "text", prompt
        );
        Map<String, Object> partImage = Map.of(
                "inline_data", inlineData
        );
        Map<String, Object> parts = Map.of(
                "parts", java.util.List.of(partText, partImage)
        );
        Map<String, Object> content = Map.of(
                "contents", java.util.List.of(parts)
        );
        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(":generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(content)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        String text = null;
        JsonNode candidates = root.path("candidates");
        if (candidates.isArray() && candidates.size() > 0) {
            JsonNode contentNode = candidates.get(0).path("content");
            JsonNode partsNode = contentNode.path("parts");
            if (partsNode.isArray() && partsNode.size() > 0) {
                text = partsNode.get(0).path("text").asText();
            }
        }
        JsonNode usageMetadata = root.path("usageMetadata");
        return new GeminiResponseDTO(text, usageMetadata);
    }
}
