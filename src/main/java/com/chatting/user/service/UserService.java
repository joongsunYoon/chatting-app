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

    public MatchedResponseDto isMatched(Long userId) {
        try {
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
            return MatchedResponseDto.fromEntity(user);

        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_DATA);

        }
    }


    public Integer getUserAffinityQuantity(Long userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        return user.getAffinityQuantity();

    }

    public Integer updateAffinityQuantity(Long userId, Integer quantity) {

        quantity = userRepository.findByUserId(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND))
                .getAffinityQuantity() + quantity;
        userRepository.updateAffinityQuantity(userId, quantity);
        return quantity;
    }

}
