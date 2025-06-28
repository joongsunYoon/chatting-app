package com.chatting.pubnub.service;

import com.chatting.pubnub.config.PubNubConfig;
import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.history.PNFetchMessageItem;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PubnubPublishService {

    private final PubNubConfig pubNubConfig;
    private PubNub pubnub;

    @PostConstruct
    public void init() {
        try {
            pubnub = pubNubConfig.pubNubClient();

        } catch (PubNubException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String channel, String message) {
        pubnub.publish()
                .channel(channel)
                .message(message)
                .async(result -> {
                    if(result.isSuccess()) {
                        System.out.println("Sent message with timetoken: " + result.getOrNull().getTimetoken());
                    }else{
                        System.err.println("Got error: " + result.exceptionOrNull());
                    }
                });
    }

    public void receiveMessages(String channel) {

        // 채널 구독 시작
        pubnub.subscribe()
                .channels(List.of(channel))
                .execute();

        pubnub.fetchMessages()
                .channels(List.of(channel))
                .includeMessageActions(true)
                .async(result -> {
                    System.out.println("Received messages with timetoken: " + result.getOrNull());
                    if(result.isSuccess()) {
                        List<PNFetchMessageItem> list = result.getOrNull().component1().get(channel);
                        for(PNFetchMessageItem item : list) {
                            System.out.println("Received message with timetoken: " + item.getMessage() + ", " + item.getTimetoken());
                        }
                    }else{
                        System.err.println("Got error: " + result.exceptionOrNull());
                    }
                });

    }

    public void addListener(String channel) {
        // 메시지 수신 리스너 등록
        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {
                System.out.println("Status: " + status.getCategory());
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                System.out.println("Message: " + message.getMessage());
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
                System.out.println("Presence: " + presence.getEvent());
            }
        });

        // 채널 구독 시작
        pubnub.subscribe()
                .channels(List.of(channel))
                .execute();

    }


}
