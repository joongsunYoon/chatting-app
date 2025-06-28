package com.chatting.chatRoom.repository;

import com.chatting.chatRoom.model.ChatRoomMember;
import com.chatting.chatRoom.model.ChatRoom;
import com.chatting.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomMembersRepository extends JpaRepository<ChatRoomMember, Long> {

    Optional<ChatRoomMember> findByUserUserId(Long userUserId);
}