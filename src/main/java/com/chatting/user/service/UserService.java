package com.chatting.user.service;
import com.chatting.user.dto.UserRegistrationDto;
import com.chatting.user.model.Users;
import com.chatting.user.repository.UsersRepository;
import com.chatting.user.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UsersRepository userRepository;
    private final UserSessionRepository userSessionRepository;

    public UserService(UsersRepository userRepository, UserSessionRepository userSessionRepository) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
    }

    public Users registerUser(UserRegistrationDto userRegistrationDto) {
        Users user = Users.builder()
                .username(userRegistrationDto.getUsername())
                .email(userRegistrationDto.getEmail())
                .password(userRegistrationDto.getPassword())
                .name(userRegistrationDto.getName())
                .phone(userRegistrationDto.getPhone())
                .status("ACTIVE")
                .build();

        return userRepository.save(user);
    }


    public boolean login(String username, String password) {

        return userRepository.findByUsername(username)
                .map(user -> { return user.getPassword().equals(password);})
                .orElse(false);
    }

    public Optional<Users> findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }
}
