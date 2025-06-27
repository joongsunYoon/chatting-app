package com.chatting.user.service;
import com.chatting.global.exception.CustomException;
import com.chatting.global.exception.ErrorCode;
import com.chatting.user.dto.MatchedResponseDto;
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

    public MatchedResponseDto isMatched(Long userId) {
        try {
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
            return MatchedResponseDto.fromEntity(user);

        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_DATA);

        }
    }

    public void updateIsMatched(Long userId) {
        try {
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));

            user.setIsMatched(true);  // 상태 업데이트
            userRepository.save(user);

        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_DATA);

        }
    }

    public Integer getUserAffinityQuantity(Long userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        return user.getAffinityQuantity();

    }



}
