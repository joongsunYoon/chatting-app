package com.chatting.chatMessage.service;

import com.chatting.chatMessage.dto.MessageRequestDto;
import com.chatting.chatMessage.model.ChatMessage;
import com.chatting.chatMessage.repository.MessageRepository;
import com.chatting.chattingRoom.model.ChatRoom;
import com.chatting.user.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public ChatMessage saveMessage(MessageRequestDto dto) {
        ChatMessage message = ChatMessage.builder()
                .sender(Users.builder().userId(dto.getSenderId()).build()) // FK 참조
                .chatRoom(ChatRoom.builder().roomId(dto.getRoomId()).build())
                .message(dto.getMessage())
                .sentAt(Timestamp.valueOf(LocalDateTime.now()))
                .isRead(false)
                .build();

        return messageRepository.save(message);
    }
}
