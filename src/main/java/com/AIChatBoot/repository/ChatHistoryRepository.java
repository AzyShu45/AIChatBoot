package com.AIChatBoot.repository;

import com.AIChatBoot.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Optional, but helps during debugging
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
}