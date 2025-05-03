package com.chatting.chattingRoom.repository;

import com.chatting.chattingRoom.model.ChatRoomMember;
import com.chatting.chattingRoom.model.ChatRoom;
import com.chatting.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomMembersRepository extends JpaRepository<ChatRoomMember, Long> {

    // 1. 특정 채팅방의 모든 멤버 조회
    List<ChatRoomMember> findByChatRoom(ChatRoom chatRoom);

    // 2. 특정 유저가 속한 모든 채팅방 멤버 엔티티 조회
    List<ChatRoomMember> findByUser(Users user);

    // 3. 특정 유저가 특정 채팅방에 속해 있는지 확인
    Optional<ChatRoomMember> findByChatRoomAndUser(ChatRoom chatRoom, Users user);

    // 4. 채팅방에 속한 유저 수 조회
    Long countByChatRoom(ChatRoom chatRoom);
}