package com.chatting.affinity.dto;

import com.chatting.affinity.model.Affinity;
import lombok.Getter;

@Getter
public class AffinityRequestDto {
    private Long userId;
    private Long affinityScore;

}
