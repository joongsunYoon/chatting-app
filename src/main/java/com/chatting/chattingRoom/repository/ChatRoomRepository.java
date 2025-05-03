package com.chatting.chattingRoom.repository;

import com.chatting.chattingRoom.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}

