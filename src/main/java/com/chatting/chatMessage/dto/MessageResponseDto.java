package com.chatting.chatMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageResponseDto {
    private Long messageId;
    private String message;
    private LocalDateTime sentAt;
    private Boolean isRead;
    private Long senderId;
}

