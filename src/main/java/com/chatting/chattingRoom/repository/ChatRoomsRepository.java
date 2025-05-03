package com.chatting.chattingRoom.repository;

import com.chatting.chattingRoom.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomsRepository extends JpaRepository<ChatRoom, Long> {

}

