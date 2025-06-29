package com.chatting.chatMessage.service;

import com.chatting.chatMessage.dto.MessageRequestDto;
import com.chatting.chatMessage.dto.MessageResponseDto;
import com.chatting.chatMessage.model.ChatMessage;
import com.chatting.chatMessage.repository.MessageRepository;
import com.chatting.chatRoom.model.ChatRoom;
import com.chatting.global.exception.CustomException;
import com.chatting.global.exception.ErrorCode;
import com.chatting.user.model.Users;
import com.chatting.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UsersRepository usersRepository;

    public ChatMessage saveMessage(MessageRequestDto dto) {
        ChatMessage message = ChatMessage.builder()
                .sender(Users.builder().userId(dto.getSenderId()).build()) // FK 참조
                .chatRoom(ChatRoom.builder().chatRoomId(dto.getRoomId()).build())
                .message(dto.getMessage())
                .sentAt(Timestamp.valueOf(LocalDateTime.now()))
                .isRead(false)
                .build();

        return messageRepository.save(message);
    }

    public List<MessageResponseDto> getSecretChatRoom(Long userId) {
        Long roomId = usersRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        ).getIsMatched();
        return messageRepository.findByChatRoomChatRoomId(roomId).stream().map(MessageResponseDto::fromEntity).toList();
    }
}
