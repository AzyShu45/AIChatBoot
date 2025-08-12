package com.AIChatBoot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Final Gemini AI response object")
public class GeminiResponseDTO {

    @Schema(description = "Original prompt submitted", example = "Tell me a joke about computers")
    private String prompt;

    @Schema(description = "Response returned by Gemini AI", example = "Why did the computer get cold? It left its Windows open!")
    private String response;
}