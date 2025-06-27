package com.chatting.pubnub.config;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.enums.PNReconnectionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class PubNubConfig {
    @Bean
    public PubNub pubNubClient() throws PubNubException {
        PNConfiguration pnConfig = new PNConfiguration("spring-chat-server");
        pnConfig.setSubscribeKey("sub-c-f6869e32-61b1-497b-98b0-d6f084e08a34");
        pnConfig.setPublishKey("pub-c-81efa55b-2f4d-4c1c-a3b9-7c274f59f88c");
        pnConfig.setUuid("spring-chat-server");
        pnConfig.setReconnectionPolicy(PNReconnectionPolicy.LINEAR);

        //PubNub 생성
        PubNub pubnub = PubNub.create(pnConfig);
        return pubnub;
    }

}

