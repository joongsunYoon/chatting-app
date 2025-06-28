package com.chatting.pubnub.controller;

import com.chatting.global.exception.GlobalExceptionHandler.ApiResponse;
import com.chatting.pubnub.config.PubNubConfig;
import com.chatting.pubnub.service.PubnubPublishService;
import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pubnub")
@RequiredArgsConstructor
public class PubnubController {

    private final PubNub pubNub;
    private final PubnubPublishService pubnubPublishService;

    @PostMapping("start")
    public ResponseEntity<ApiResponse<String>> start() {
        pubnubPublishService.init();
        return ResponseEntity.ok(new ApiResponse<>(200 , "채팅연결 성공" , null));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> sendMessage(@RequestParam String message) {

        pubnubPublishService.sendMessage("test_channel", message);
        return ResponseEntity.ok(new ApiResponse<>(200 , "전송 성공" , null));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<String>> receiveMessage(@RequestParam String channel) {

        pubnubPublishService.receiveMessages(channel);
        return ResponseEntity.ok(new ApiResponse<>(200 , "전송 성공" , null));
    }

    //실시간 통신 지원하는 용도인 듯
    @PostMapping("addListener")
    public ResponseEntity<ApiResponse<Void>> addListener(@RequestParam String channel){
        pubnubPublishService.addListener(channel);
        return ResponseEntity.ok(new ApiResponse<>(200,"수신 성공",null));
    }








}


