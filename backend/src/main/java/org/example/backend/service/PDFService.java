package org.example.backend.service;

import org.example.backend.dto.AIDTO.OpenRouterResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class PDFService {
    private final WebClient webClient;

    private final String apiKey;

    private final   String apiUrl;

    public PDFService(WebClient.Builder builder,@Value("${openrouter.api.key}") String apiKey, @Value("${openrouter.api.url}") String apiUrl) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.webClient = builder.baseUrl(apiUrl).build();
    }


    public OpenRouterResponseDTO processAndSendToOpenRouter(MultipartFile file) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(file.getBytes());

        Map<String, Object> fileMap = Map.of(
                "filename", file.getOriginalFilename(),
                "file_data", "data:application/pdf;base64," + base64
        );

        Map<String, Object> contentText = Map.of(
                "type", "text",
                "text", "What are the main points in this document?"
        );

        Map<String, Object> contentFile = Map.of(
                "type", "file",
                "file", fileMap
        );

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", List.of(contentText, contentFile)
        );

        Map<String, Object> requestBody = Map.of(
                "model", "google/gemma-3-27b-it",
                "messages", List.of(message),
                "plugins", List.of(Map.of(
                        "id", "file-parser",
                        "pdf", Map.of("engine", "pdf-text")
                ))
        );

        return webClient.post()
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ apiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenRouterResponseDTO.class)
                .block();
    }
}
