package com.chatting.affinity.repository;

import com.chatting.affinity.model.Affinity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AffinityRepository extends JpaRepository<Affinity, Long> {

    @Query("SELECT a FROM Affinity a WHERE a.fromUser.userId = :userId")
    List<Affinity> findAllByfromUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Affinity a WHERE a.fromUser.userId = :fromUserId and a.toUser.userId = :toUserId")
    Optional<Affinity> findByFromAndToUserId(@Param("fromUserId") Long fromUserId,@Param("toUserId") Long toUserId);

}
