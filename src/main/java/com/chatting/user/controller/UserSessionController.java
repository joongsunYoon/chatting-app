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

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "user/login"; // user/login html에 연결
    }


    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto requestDto, HttpServletRequest request, Model model) {
        boolean success = userService.login(requestDto.getUsername(), requestDto.getPassword());
        if (success) {
            request.getSession().setAttribute("username", requestDto.getUsername());

            return "redirect:/"; // 홈으로 리다이렉트
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
            return "user/login"; // 다시 로그인 페이지
        }
    }

    @GetMapping("/isMatched")
    public ResponseEntity<ApiResponse> isMatched(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.validateAndGetUserId(token);

        MatchedResponseDto dto = userService.isMatched(userId);
        return ResponseEntity.ok(new ApiResponse(200 , "결과 출력되었습니다", dto));

    }
}
