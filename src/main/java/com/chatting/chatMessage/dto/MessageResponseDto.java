package com.chatting.chatMessage.dto;

import com.chatting.chatMessage.model.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageResponseDto {
    private Long messageId;
    private String message;
    private Timestamp sentAt;
    private Long senderId;

    public static MessageResponseDto fromEntity(ChatMessage entity) {
        return new MessageResponseDto(
                entity.getMessageId(),
                entity.getMessage(),
                entity.getSentAt(),
                entity.getSender().getUserId()
                );
    }
}

