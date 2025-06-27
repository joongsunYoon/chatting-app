package com.chatting.friend.service;

import com.chatting.friend.dto.FriendListResponseDto;
import com.chatting.friend.dto.FriendUpdateRequestDto;
import com.chatting.friend.model.Friend;
import com.chatting.friend.repository.FriendRepository;
import com.chatting.global.exception.CustomException;
import com.chatting.global.exception.ErrorCode;
import com.chatting.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UsersRepository usersRepository;

    public void updateFriendStatus(Long userId, FriendUpdateRequestDto dto) {
        Friend friend = friendRepository.findFriendRelation(userId, dto.getFriendId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

        try {
            friend.setStatus(dto.getStatus());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_DATA); // 상태값이 잘못되었을 경우
        }

        friendRepository.save(friend);
    }

    public List<FriendListResponseDto> getFriendList(Long userId) {
        List<FriendListResponseDto> friendList = new ArrayList<>();

        List<Friend> sent = friendRepository.findAllByUserIdAndStatus(userId, Friend.FriendStatus.accepted);
        for (Friend f : sent) {
            usersRepository.findById(f.getFriendUser().getUserId()).ifPresent(user ->
                    friendList.add(FriendListResponseDto.fromEntity(user))
            );
        }

        return friendList;
    }

}
