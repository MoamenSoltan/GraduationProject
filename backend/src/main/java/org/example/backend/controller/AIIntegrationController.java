package org.example.backend.controller;

import org.example.backend.dto.AIDTO.AnalyzeSummaryDTO;
import org.example.backend.dto.AIDTO.Choice;
import org.example.backend.dto.AIDTO.GeminiResponseDTO;
import org.example.backend.dto.AIDTO.OpenRouterResponseDTO;
import org.example.backend.service.GeminiService;
import org.example.backend.service.PDFService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AIIntegrationController {
    private final PDFService pdfService;
    private final GeminiService geminiService;

    public AIIntegrationController(PDFService pdfService, GeminiService geminiService) {
        this.pdfService = pdfService;
        this.geminiService = geminiService;
    }

    @PostMapping("/summarize")
    public ResponseEntity<?> summarizePDF(@RequestParam("file") MultipartFile file) {
        try {
            OpenRouterResponseDTO result = pdfService.processAndSendToOpenRouter(file);
            String id = result.getId();
            String summary = null;
            if (result.getChoices() != null && !result.getChoices().isEmpty()) {
                Choice choice = result.getChoices().get(0);
                if (choice.getMessage() != null) {
                    summary = choice.getMessage().getContent();
                }
            }
            AnalyzeSummaryDTO response = new AnalyzeSummaryDTO(id, summary);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> askGeminiWithImage(@RequestParam("image") MultipartFile image,
                                                @RequestParam(value = "prompt", defaultValue = "What is this picture?") String prompt) {
        try {
            GeminiResponseDTO result = geminiService.askGeminiWithImage(image, prompt);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
