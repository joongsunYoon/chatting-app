package com.chatting.friend.dto;

import com.chatting.friend.model.Friend;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendUpdateRequestDto {
    private Long friendId;
    private Friend.FriendStatus status;
}
