package com.chatting.dev;

import com.chatting.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dev")
public class DevController {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/token")
    public String getToken() {
        return jwtTokenProvider.generateToken(101L);
    }
}



