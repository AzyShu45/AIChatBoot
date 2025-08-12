package com.AIChatBoot.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;

    private String response;

    @Column(name = "user_id", nullable = false)
    private String userId;

}