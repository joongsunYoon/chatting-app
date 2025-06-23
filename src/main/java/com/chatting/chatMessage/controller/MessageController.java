package com.chatting.chatMessage.controller;

import com.chatting.chatMessage.dto.MessageRequestDto;
import com.chatting.chatMessage.model.ChatMessage;
import com.chatting.chatMessage.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody MessageRequestDto requestDto) {
        ChatMessage savedMessage = messageService.saveMessage(requestDto);
        return ResponseEntity.ok(savedMessage);
    }
}

