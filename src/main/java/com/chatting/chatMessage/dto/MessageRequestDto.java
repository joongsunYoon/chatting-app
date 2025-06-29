package com.chatting.chatMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequestDto {
    private String message;
    private Long roomId;
    private Long senderId;
}

