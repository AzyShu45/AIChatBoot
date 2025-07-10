# 🧠 AIChatBoot – Gemini AI Chatbot with Spring Boot

AIChatBoot is a full-stack chatbot application powered by Google's **Gemini AI API**, built with **Spring Boot**, **Thymeleaf**, **MySQL**, and documented using **Swagger UI**. It features both a RESTful API and a frontend web interface for real-time chat, response logging, and API testing.

---

## 🚀 Features

- 💬 Chat with Gemini AI (via REST or web UI)
- 🗃️ Stores chat history with user ID in MySQL
- 🌐 Clean Swagger UI for API testing
- ⚡ Rate limiting via custom servlet filter
- 🛡️ Modular codebase with DTOs, services, config, and controllers
- 🎨 Responsive Thymeleaf frontend UI
- 📈 Logs all prompts and responses with timestamps

---

## 🔧 Technologies & Skills Used

| Category       | Tools / Libraries                                   |
|----------------|-----------------------------------------------------|
| Backend        | Java 17, Spring Boot 3, Spring MVC, Spring Data JPA |
| Frontend       | Thymeleaf, HTML5, CSS3                              |
| AI Integration | Google Gemini API (Generative Language API)         |
| API Docs       | SpringDoc OpenAPI 3 (Swagger UI)                    |
| Database       | MySQL (also supports H2 for testing)                |
| Rate Limiting  | Custom Filter using `FilterRegistrationBean`        |
| Build Tool     | Maven                                               |
| Logging        | SLF4J + Logback                                     |

---

## 📁 Project Structure

```
src/main/java/com/AIChatBoot
├── config/           → Swagger, Web, RestTemplate configs
├── controller/       → GeminiBotController (UI) & GeminiRestController (API)
├── dto/              → Message, GeminiRequest, GeminiResponseDTO
├── model/            → ChatHistory (JPA entity)
├── repository/       → ChatHistoryRepository (JPA repo)
├── service/          → GeminiService (AI API logic)
├── security/         → SimpleRateLimitFilter (API protection)
└── ChatgptBotApplication.java
```

---

## 🧪 REST API Endpoints

| Method | Endpoint           | Description                       |
|--------|--------------------|-----------------------------------|
| `POST` | `/api/gemini/chat` | Send a prompt and get AI response |
| `GET`  | `/gemini/chat`     | Load chatbot UI in browser        |
| `POST` | `/gemini/chat`     | Submit prompt via HTML form       |

📘 Visit Swagger: [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)  
🌐 UI Chat Page: [`http://localhost:8080/gemini/chat`](http://localhost:8080/gemini/chat)

---

## ⚙️ Setup Instructions

1. **Clone the repo**:
   ```bash
   git clone https://github.com/AzyShu45/AIChatBoot.git
   cd AIChatBoot
   ```

2. **Configure `application.properties`**:
   - Set your Gemini API Key
   - Set MySQL credentials

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access via browser**:
   - [Swagger UI](http://localhost:8080/swagger-ui/index.html)
   - [Gemini Chat UI](http://localhost:8080/gemini/chat)

---

## 🧠 Gemini Integration

All prompts are sent to:
```
POST https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=YOUR_API_KEY
```

---

## 💡 Future Enhancements

- 🧑 User authentication (Spring Security and JWT)
- 📄 Export chat history (CSV/PDF)
- 🧵 Session-based chat memory
- 📉 Usage statistics dashboard
- 🌐 Multi-language prompt support

---

## 🤝 Contributing

Pull requests are welcome! Please open an issue first to discuss what you’d like to change.

---

## 📄 License

This project is open-source and available under the MIT License.
