package com.chatting.affinity.repository;

import com.chatting.affinity.model.Affinity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AffinityRepository extends JpaRepository<Affinity, Long> {

    @Query("""
        SELECT a FROM Affinity a
        WHERE EXISTS (
            SELECT 1 FROM Affinity b
            WHERE b.toUser.userId = a.fromUser.userId AND b.fromUser.userId = a.toUser.userId
        )
    """)
    List<Affinity> findAllMutualAffinities();
}
