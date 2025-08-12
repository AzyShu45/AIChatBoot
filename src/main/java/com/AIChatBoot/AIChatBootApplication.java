package com.AIChatBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.AIChatBoot")
@EnableJpaRepositories(basePackages = "com.AIChatBoot.repository")
public class AIChatBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AIChatBootApplication.class, args);
        System.out.println("🚀 Gemini AI Chat Bot is running!");
        System.out.println("🌐 UI Chat Page: http://localhost:8080/gemini/chat");
        System.out.println("📘 Swagger Docs: http://localhost:8080/swagger-ui/index.html");
        System.out.println("📬 Test API:     POST /api/gemini/chat?prompt=Your+question");
    }

}