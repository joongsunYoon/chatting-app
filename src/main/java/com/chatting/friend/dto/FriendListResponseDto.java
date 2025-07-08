package com.chatting.friend.dto;

import com.chatting.friend.model.Friend;
import com.chatting.user.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendListResponseDto {
    private Long userId;
    private String name;
    private String nickname;
    private String phone;
    private String bio;
    private LocalDate birthdate;
    private String profile;

    public static FriendListResponseDto fromEntity(Users user) {
        return FriendListResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickname())
                .phone(user.getPhone())
                .bio(user.getBio())
                .birthdate(user.getBirthdate())
                .profile(user.getProfile())
                .build();
    }

}
