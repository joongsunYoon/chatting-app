package com.chatting.pubnub.config;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.enums.PNReconnectionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class PubNubConfig {

    @Bean
    public PubNub pubNubClient() throws PubNubException {
        PNConfiguration pnConfig = new PNConfiguration("spring-chat-server");
        pnConfig.setSubscribeKey("sub-c-d3a89a6e-0af0-4f31-a506-420aa076f71e");
        pnConfig.setPublishKey("pub-c-6128c948-fad3-4232-b209-10000b317f0c");
        pnConfig.setUuid("spring-chat-server");
        pnConfig.setReconnectionPolicy(PNReconnectionPolicy.LINEAR);

        //PubNub 생성
        PubNub pubnub = PubNub.create(pnConfig);
        return pubnub;
    }

}

