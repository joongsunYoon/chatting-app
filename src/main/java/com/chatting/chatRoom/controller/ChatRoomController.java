package com.chatting.chatRoom.controller;

import com.chatting.chatRoom.service.ChatRoomService;
import com.chatting.security.JwtTokenProvider;
import com.chatting.user.dto.MatchedResponseDto;
import com.chatting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chatting.global.exception.GlobalExceptionHandler.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 실제 사용자 ID를 지정하거나 테스트용으로 고정해도 됨
    @PostMapping("/match")
    public ResponseEntity<ApiResponse<MatchedResponseDto>> createChatRoom(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.validateAndGetUserId(token);

        chatRoomService.createChatRoomForUser(userId);
        userService.updateIsMatched(userId);
        MatchedResponseDto dto = userService.isMatched(userId);

        return ResponseEntity.ok(new ApiResponse<>(200, "match update 완료" , dto));
    }
}

