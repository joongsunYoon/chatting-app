package com.chatting.chatMessage.controller;

import com.chatting.chatMessage.dto.MessageRequestDto;
import com.chatting.chatMessage.dto.MessageResponseDto;
import com.chatting.chatMessage.model.Message;
import com.chatting.chatMessage.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> sendMessage(@RequestBody MessageRequestDto requestDto) {
        return ResponseEntity.ok(messageService.sendMessage(requestDto));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<MessageResponseDto>> getMessagesByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(messageService.getMessagesByRoom(roomId));
    }

}

