package com.chatting.chatMessage.dto;

import lombok.Data;

@Data
public class MessageRequestDto {
    private String message;
    private Long roomId;
    private Long senderId;
}

