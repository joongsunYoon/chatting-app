package com.chatting.affinity.service;

import com.chatting.affinity.dto.AffinityRequestDto;
import com.chatting.affinity.dto.ReceivedAffinityResponseDto;
import com.chatting.affinity.model.Affinity;
import com.chatting.affinity.repository.AffinityRepository;
import com.chatting.global.exception.CustomException;
import com.chatting.global.exception.ErrorCode;
import com.chatting.user.model.Users;
import com.chatting.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class AffinityService {
    private final AffinityRepository affinityRepository;
    private final UsersRepository usersRepository;

    public List<ReceivedAffinityResponseDto> getReceivedAffinities(Long id){

        if (!usersRepository.existsById(id)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<Affinity> affinityList = affinityRepository.findAllByfromUserId(id);

        return affinityList.stream()
                .map(ReceivedAffinityResponseDto::fromEntity)
                .toList();
    }

    //호감도 변경
    public void setAffinityScore(Long id , Integer usedAffinityScore , AffinityRequestDto dto){

        if (!usersRepository.existsById(id)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Optional<Affinity> optionalAffinity = affinityRepository.findByFromAndToUserId(id , dto.getUserId());
        Affinity affinity;

        if(optionalAffinity.isPresent()){
            affinity = optionalAffinity.get();
            affinity.setAffinityScore(dto.getAffinityScore() +  usedAffinityScore);
        }else{
            Users fromUser = usersRepository.findById(id)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            Users toUser = usersRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            affinity = Affinity.builder()
                    .fromUser(fromUser)
                    .toUser(toUser)
                    .affinityScore(Long.valueOf(usedAffinityScore))
                    .lastChangeDirection(Affinity.Direction.PLUS)
                    .build();
        }

        affinityRepository.save(affinity);
    }


}
