package com.chatting.pubnub.service;

import com.chatting.chatMessage.service.MessageService;
import com.chatting.pubnub.config.PubNubConfig;
import com.google.gson.Gson;
import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.models.consumer.PNStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
public class PubnubPublishService {

    private final MessageService messageService;
    private final Gson gson = new Gson();
    private final PubNubConfig pubNubConfig;

    @PostConstruct
    public void init() {
        try {
            PubNub pubnub = pubNubConfig.pubNubClient();

            // 메시지 수신 리스너 등록
            pubnub.addListener(new com.pubnub.api.callbacks.SubscribeCallback() {
                @Override
                public void status(PubNub pubnub, PNStatus status) {
                    System.out.println("Status: " + status.getCategory());
                }

                @Override
                public void message(PubNub pubnub, com.pubnub.api.models.consumer.pubsub.PNMessageResult message) {
                    System.out.println("Message: " + message.getMessage());
                }

                @Override
                public void presence(PubNub pubnub, com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult presence) {
                    System.out.println("Presence: " + presence.getEvent());
                }
            });

            // 채널 구독 시작
            pubnub.subscribe()
                    .channels(List.of("chat-room-1", "chat-room-2"))
                    .execute();

        } catch (PubNubException e) {
            e.printStackTrace();
        }
    }
}
