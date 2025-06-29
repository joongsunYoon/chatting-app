package com.chatting.pubnub.controller;

import com.chatting.chatMessage.dto.MessageRequestDto;
import com.chatting.chatMessage.service.MessageService;
import com.chatting.chatRoom.service.ChatRoomService;
import com.chatting.global.exception.ErrorCode;
import com.chatting.global.exception.GlobalExceptionHandler.ApiResponse;
import com.chatting.pubnub.config.PubNubConfig;
import com.chatting.pubnub.service.PubnubPublishService;
import com.chatting.security.JwtTokenProvider;
import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/pubnub")
@RequiredArgsConstructor
public class PubnubController {

    private final PubnubPublishService pubnubPublishService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    @PostMapping("start")
    public ResponseEntity<ApiResponse<String>> start() {
        pubnubPublishService.init();
        return ResponseEntity.ok(new ApiResponse<>(200 , "채팅연결 성공" , null));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> sendMessage(@RequestHeader("Authorization") String authHeader,
                                                           @RequestParam Long userId ,
                                                           @RequestParam String message,
                                                           @RequestParam String channel) {
        String token = authHeader.replace("Bearer ", "");
        Long userIdByAuth = jwtTokenProvider.validateAndGetUserId(token);

        //token과 userid 대조
        if(!Objects.equals(userIdByAuth, userId)){
            return ResponseEntity.ok(new ApiResponse<>(ErrorCode.INVALID_TOKEN));
        }

        Long roomId = chatRoomService.getChatRoomId(userIdByAuth);

        //요청한 channel에 해당 Userid가 있는지 (임시)
        if(!Objects.equals(roomId, Long.valueOf(channel))){
            return ResponseEntity.ok(new ApiResponse<>(ErrorCode.DATA_NOT_FOUND));
        }

        pubnubPublishService.sendMessage(channel, message);
        messageService.saveMessage(new MessageRequestDto(message , Long.valueOf(channel) , userId));
        return ResponseEntity.ok(new ApiResponse<>(200 , "채팅 전송 완료" , null));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<String>>> receiveMessage(@RequestHeader("Authorization") String authHeader,
                                                                    @RequestParam Long userId,
                                                                    @RequestParam String channel) {

        String token = authHeader.replace("Bearer ", "");
        Long userIdByAuth = jwtTokenProvider.validateAndGetUserId(token);

        //token과 userid 대조
        if(!Objects.equals(userIdByAuth, userId)){
            return ResponseEntity.ok(new ApiResponse<>(ErrorCode.INVALID_TOKEN));
        }

        Long roomId = chatRoomService.getChatRoomId(userIdByAuth);

        //요청한 channel에 해당 Userid가 있는지 (임시)
        if(!Objects.equals(roomId, Long.valueOf(channel))){
            return ResponseEntity.ok(new ApiResponse<>(ErrorCode.DATA_NOT_FOUND));
        }

        return ResponseEntity.ok(new ApiResponse<>(200 , "채팅 목록 조회 완료" , pubnubPublishService.receiveMessages(channel)));
    }

    //실시간 통신 지원하는 용도인 듯
    @PostMapping("addListener")
    public ResponseEntity<ApiResponse<Void>> addListener(@RequestHeader("Authorization") String authHeader,
                                                         @RequestParam Long userId,
                                                         @RequestParam String channel){

        String token = authHeader.replace("Bearer ", "");
        Long userIdByAuth = jwtTokenProvider.validateAndGetUserId(token);

        //token과 userid 대조
        if(!Objects.equals(userIdByAuth, userId)){
            return ResponseEntity.ok(new ApiResponse<>(ErrorCode.INVALID_TOKEN));
        }

        Long roomId = chatRoomService.getChatRoomId(userIdByAuth);

        //요청한 channel에 해당 Userid가 있는지 (임시)
        if(!Objects.equals(roomId, Long.valueOf(channel))){
            return ResponseEntity.ok(new ApiResponse<>(ErrorCode.DATA_NOT_FOUND));
        }

        pubnubPublishService.addListener(channel);
        return ResponseEntity.ok(new ApiResponse<>(200,"수신 성공",null));
    }



}


