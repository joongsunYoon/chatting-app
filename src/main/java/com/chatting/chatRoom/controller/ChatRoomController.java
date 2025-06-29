package com.chatting.chatRoom.controller;

import com.chatting.chatMessage.dto.MessageResponseDto;
import com.chatting.chatMessage.model.ChatMessage;
import com.chatting.chatMessage.service.MessageService;
import com.chatting.chatRoom.service.ChatRoomService;
import com.chatting.global.exception.CustomException;
import com.chatting.global.exception.ErrorCode;
import com.chatting.security.JwtTokenProvider;
import com.chatting.user.dto.MatchedResponseDto;
import com.chatting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chatting.global.exception.GlobalExceptionHandler.ApiResponse;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MessageService messageService;

    // 실제 사용자 ID를 지정하거나 테스트용으로 고정해도 됨
    @PostMapping("/match")
    public ResponseEntity<ApiResponse<Long>> createChatRoom(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.validateAndGetUserId(token);

        if(userService.isMatched(userId).getIsMatched() != 0L){
            return ResponseEntity.ok(new ApiResponse<>(500,"이미 매칭되어있습니다",null));
        }
        Long roomId = chatRoomService.createChatRoomForUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "match update 완료" , roomId));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MessageResponseDto>>> getSecretChatRoom(@RequestHeader("Authorization") String authHeader,
                                                                       @RequestParam Long userId) {
        String token = authHeader.replace("Bearer ", "");
        Long userIdByAuth = jwtTokenProvider.validateAndGetUserId(token);

        //token과 userid 대조
        if(!Objects.equals(userIdByAuth, userId)){
            return ResponseEntity.ok(new ApiResponse<>(ErrorCode.INVALID_TOKEN));
        }

        List<MessageResponseDto> list = messageService.getSecretChatRoom(userId);
        return ResponseEntity.ok(new ApiResponse<>(200 , "조회에 성공했습니다" , list));


    }





}

