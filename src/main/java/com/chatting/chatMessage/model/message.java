package com.chatting.chatMessage.model;

import com.chatting.chattingRoom.model.ChatRoom;
import com.chatting.user.model.Users;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class message {
    @Id
    @GeneratedValue
    private Long messageId;

    private String message;
    private LocalDateTime sentAt;
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Users sender;
}
