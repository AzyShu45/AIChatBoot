package com.AIChatBoot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Schema(description = "User role (e.g., 'user')", example = "user")
    private String role;

    @Schema(description = "Prompt content to send to Gemini", example = "Write a short poem about AI")
    private String content;
}