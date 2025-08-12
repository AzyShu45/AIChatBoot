package com.AIChatBoot.controller;

import com.AIChatBoot.dto.GeminiResponseDTO;
import com.AIChatBoot.dto.Message;
import com.AIChatBoot.service.GeminiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
@Tag(name = "Gemini API", description = "Gemini AI Bot REST API")
public class GeminiRestController {


    private final GeminiService geminiService;

    public GeminiRestController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @Operation(summary = "Send prompt and get Gemini AI response",
            description = "Submit a user prompt and receive a response from Gemini AI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Gemini AI response",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeminiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid prompt"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/chat")
    public ResponseEntity<GeminiResponseDTO> getGeminiResponse(@RequestBody Message message) {
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String responseText = geminiService.getGeminiResponseText(message.getContent());
        GeminiResponseDTO dto = new GeminiResponseDTO(message.getContent(), responseText);
        return ResponseEntity.ok(dto);
    }
}