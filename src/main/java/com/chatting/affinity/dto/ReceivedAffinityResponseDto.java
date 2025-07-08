package com.chatting.affinity.dto;

import com.chatting.affinity.model.Affinity;
import com.chatting.user.model.Users;
import lombok.Getter;

@Getter
public class ReceivedAffinityResponseDto {
    private final Long userId;
    private final String nickname;
    private final String name;
    private final String bio;
    private final String phone;
    private final Long affinityScore;


    private ReceivedAffinityResponseDto(Long userId, String nickname, String name, String bio, String phone, Long affinityScore) {
        this.userId = userId;
        this.nickname = nickname;
        this.name = name;
        this.bio = bio;
        this.phone = phone;
        this.affinityScore = affinityScore;
    }

    public static ReceivedAffinityResponseDto fromEntity(Affinity affinity) {
        Users user = affinity.getToUser();
        return new ReceivedAffinityResponseDto(
                user.getUserId(),
                user.getNickname(),
                user.getName(),
                user.getBio(),
                user.getPhone(),
                affinity.getAffinityScore()
                );
    }
}
