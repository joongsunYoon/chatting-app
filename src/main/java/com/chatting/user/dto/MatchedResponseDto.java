package com.chatting.user.dto;

import com.chatting.user.model.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchedResponseDto {
    Boolean isMatched;

    public static MatchedResponseDto fromEntity(Users user) {
        return MatchedResponseDto.builder()
                .isMatched(user.getIsMatched())
                .build();
    }
}
