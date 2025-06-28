package com.chatting.chatRoom.model;

import com.chatting.user.model.Users;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "chat_room_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomMember {

    @Id
    @Column(name = "chat_room_members_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomMembersId;

    @ManyToOne
    @JoinColumn(name = "room_id",nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;


    @Column(name = "joined_at")
    private Timestamp joinedAt;

    @PrePersist
    public void onJoined() {
        this.joinedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public static ChatRoomMember of(ChatRoom room, Users user) {
        return ChatRoomMember.builder()
                .chatRoom(room)
                .user(user)
                .joinedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }


}
