package com.chatting.chatMessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatting.chatMessage.model.ChatMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
}

