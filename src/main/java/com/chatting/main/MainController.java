package com.chatting.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 루트 경로 '/'에서 chatroom.html을 렌더링
    @GetMapping("/")
    public String home() {
        return "chatroom/chat-room-1"; // resources/templates/chatroom.html
    }

    // /chatroom 경로에서 chatroom.html을 렌더링
    @GetMapping("/chatroom/chatroom")
    public String chatRoom() {
        return "chatroom/chatroom"; // resources/templates/chatroom.html
    }
}
