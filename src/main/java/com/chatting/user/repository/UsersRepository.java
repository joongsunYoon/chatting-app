package com.chatting.user.repository;

import com.chatting.user.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository 
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.affinityQuantity = :quantity WHERE u.userId = :userId")
    void updateAffinityQuantity(@Param("userId") Long userId ,
                                           @Param("quantity") Integer quantity);
}