package com.chatting.user.controller;

import com.chatting.security.JwtTokenProvider;
import com.chatting.user.dto.LoginRequestDto;
import com.chatting.user.dto.MatchedResponseDto;
import com.chatting.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import com.chatting.global.exception.GlobalExceptionHandler.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sessions")
public class UserSessionController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/isMatched")
    public ResponseEntity<ApiResponse> isMatched(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.validateAndGetUserId(token);

        MatchedResponseDto dto = userService.isMatched(userId);
        return ResponseEntity.ok(new ApiResponse(200 , "결과 출력되었습니다", dto));

    }
}
