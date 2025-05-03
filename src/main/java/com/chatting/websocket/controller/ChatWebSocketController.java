package com.chatting.websocket.controller;

import com.chatting.chatMessage.dto.MessageRequestDto;
import com.chatting.chatMessage.dto.MessageResponseDto;
import com.chatting.chatMessage.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")  // 클라이언트가 보내는 경로
    public void send(MessageRequestDto requestDto) {
        // DB 저장
        MessageResponseDto saved = messageService.sendMessage(requestDto);

        // 채팅방 구독자에게 메시지 전송
        messagingTemplate.convertAndSend(
                "/topic/chat/" + requestDto.getRoomId(),
                saved
        );
    }
}
