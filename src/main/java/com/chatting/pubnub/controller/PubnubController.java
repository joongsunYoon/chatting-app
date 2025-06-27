package com.chatting.pubnub.controller;

import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/pubnub")
@RequiredArgsConstructor
public class PubnubController {

    private final PubNub pubNub;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        pubNub.publish()
                .channel("chat-room-1")
                .message("Hello from Spring!")
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PubNubException e) {
                        if (e == null) {
                            System.out.println("전송 성공! 타임토큰: " + result.getTimetoken());
                        } else {
                            System.err.println(" 전송 실패: " + e.getMessage());
                        }
                    }
                });

        return ResponseEntity.ok("전송 요청 완료");
    }



}


