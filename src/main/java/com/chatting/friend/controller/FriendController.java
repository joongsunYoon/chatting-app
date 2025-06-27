package com.chatting.friend.controller;

import com.chatting.friend.dto.FriendListResponseDto;
import com.chatting.friend.dto.FriendUpdateRequestDto;
import com.chatting.friend.service.FriendService;
import com.chatting.global.exception.GlobalExceptionHandler.ApiResponse;
import com.chatting.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendService friendService;
    private final JwtTokenProvider jwtTokenProvider;

    @PatchMapping("/status")
    public ResponseEntity<ApiResponse> updateFriendStatus(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody FriendUpdateRequestDto dto
    ) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.validateAndGetUserId(token);

        friendService.updateFriendStatus(userId, dto);
        return ResponseEntity.ok(new ApiResponse(200, "친구 정보 수정 완료",null));
    }


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<FriendListResponseDto>>> getFriends(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.validateAndGetUserId(token);

        List<FriendListResponseDto> friendList = friendService.getFriendList(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "친구목록 조회 성공", friendList));
    }




}
