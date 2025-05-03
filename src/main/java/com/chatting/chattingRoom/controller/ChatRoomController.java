package com.chatting.chattingRoom.controller;

import com.chatting.chattingRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 실제 사용자 ID를 지정하거나 테스트용으로 고정해도 됨
    @PostMapping("/match")
    public ResponseEntity<String> createChatRoom() {
        Long testUserId = 6L; // 테스트 사용자 ID (users 테이블에 반드시 존재해야 함)
        chatRoomService.createChatRoomForUser(testUserId);
        return ResponseEntity.ok("채팅방 생성 시도 완료 (userId=" + testUserId + ")");
    }
}

