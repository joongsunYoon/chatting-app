package com.chatting.friend.repository;

import com.chatting.friend.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface FriendRepository extends JpaRepository<Friend,Long> {
    @Query("SELECT f FROM Friend f WHERE f.user.userId = :userId AND f.friendUser.userId = :friendId")
    Optional<Friend> findFriendRelation(
            @Param("userId") Long userId,
            @Param("friendId") Long friendId
    );

    @Query("SELECT f FROM Friend f WHERE f.user.userId = :userId AND f.status = :status")
    List<Friend> findAllByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("status") Friend.FriendStatus status
    );
}
