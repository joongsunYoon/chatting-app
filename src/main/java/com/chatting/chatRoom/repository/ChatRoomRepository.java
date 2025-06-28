package com.chatting.chatRoom.repository;

import com.chatting.chatRoom.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}

