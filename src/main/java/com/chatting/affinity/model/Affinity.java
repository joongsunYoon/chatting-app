package com.chatting.affinity.model;

import com.chatting.user.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "affinity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Affinity {

    @Id
    @Column(name = "affinity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AffinityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", nullable = false)
    private Users fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id", nullable = false)
    private Users toUser;

    @Column(name = "affinity_score")
    private int affinityScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
