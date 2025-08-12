package com.AIChatBoot.controller;

import com.AIChatBoot.model.ChatHistory;
import com.AIChatBoot.repository.ChatHistoryRepository;
import com.AIChatBoot.service.GeminiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gemini")
@Tag(name = "Gemini Bot", description = "API for chatting with Gemini AI")
public class GeminiBotController {

    private static final Logger log = LoggerFactory.getLogger(GeminiBotController.class);

    private final GeminiService geminiService;
    private final ChatHistoryRepository chatRepo;

    public GeminiBotController(GeminiService geminiService, ChatHistoryRepository chatRepo) {
        this.geminiService = geminiService;
        this.chatRepo = chatRepo;
    }

    @Operation(summary = "Render Gemini chat page")
    @GetMapping("/chat")
    public String getChatPage(Model model) {
        // Adding response and prompt to the model
        String response = "Hello, how can I assist you?"; // Example response
        String prompt = "Hi, AI Assistant!";              // Example prompt
        model.addAttribute("response", response);
        model.addAttribute("prompt", prompt);

        return "chat"; // Thymeleaf template name
    }

    @Operation(summary = "Send prompt to Gemini and get response")
    @PostMapping("/chat")
    public String chatWithGemini(@RequestParam String prompt, Model model) {
        if (prompt == null || prompt.trim().isEmpty()) {
            model.addAttribute("response", "âŒ Please enter a valid prompt.");
            return "chat";
        }

        log.info("Received prompt: {}", prompt);

        String response = geminiService.generateContent(prompt); // Use generateContent

        // Save to database only if response is successful
        if (response != null && !response.startsWith("âŒ")) {
            try {
                ChatHistory chat = new ChatHistory();
                chat.setPrompt(prompt);
                chat.setResponse(response);
                // Mirror response into 'content' to satisfy NOT NULL constraint in DB
                chat.setContent(response);
                chat.setUserId("1"); // Replace with actual user ID
                chatRepo.save(chat);
                log.info("Chat history saved successfully");
            } catch (Exception e) {
                log.error("Error saving chat history:", e);
                // Continue with response even if save fails
            }
        }

        model.addAttribute("prompt", prompt);
        model.addAttribute("response", response);

        return "chat";
    }

    @GetMapping("/")
    public String redirectToChat() {
        return "redirect:/gemini/chat";
    }

    @GetMapping("*")
    @ResponseBody
    public String fallback() {
        return "âš ï¸ Unknown endpoint. Try GET /gemini/chat or POST /gemini/chat";
    }
}