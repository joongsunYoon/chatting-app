package com.chatting.user.controller;

import com.chatting.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chatting.global.exception.GlobalExceptionHandler.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/affinity-quantity")
    public ResponseEntity<ApiResponse<Integer>> getAffinityQuantity(@RequestHeader("Authorization") String authHeader,
                                                                    @PathVariable Long userId) {
        Integer affinityQuantity = userService.getUserAffinityQuantity(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Affinity 현황 조회 성공", affinityQuantity));
    }




}
