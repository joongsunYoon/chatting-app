package com.chatting.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;  // 아이디 필드 추가
    private String email;
    private String password;
    private String phone;
    private String bio;
    private LocalDate birthdate;
    private String nickname;
    private String name;
    private Long isMatched;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer affinityQuantity;
    private String profile;
}
