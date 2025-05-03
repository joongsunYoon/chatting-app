package com.chatting.user.repository;

import com.chatting.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository 
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
}