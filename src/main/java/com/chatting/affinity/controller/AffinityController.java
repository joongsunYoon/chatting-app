package com.chatting.affinity.controller;

import com.chatting.affinity.dto.AffinityRequestDto;
import com.chatting.affinity.dto.ReceivedAffinityResponseDto;
import com.chatting.affinity.service.AffinityService;
import com.chatting.global.exception.GlobalExceptionHandler.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/affinities")
public class AffinityController {

    private final AffinityService affinityService;

    //호감도 있는 친구들 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<List<ReceivedAffinityResponseDto>>> getReceivedAffinities(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long userId
    ) {
        List<ReceivedAffinityResponseDto> aList = affinityService.getReceivedAffinities(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Affinity 현황 조회 성공 ",aList));
    }

    //Affinity 수정
    @PatchMapping()
    public ResponseEntity<ApiResponse<Void>> updateFriendStatus(@RequestHeader("Authorization") String authHeader,
                                                                @RequestParam Long userId,
                                                                @RequestParam Integer usedAffinityScore,
                                                                @RequestBody AffinityRequestDto userData){
        affinityService.setAffinityScore(userId,usedAffinityScore,userData);
        return ResponseEntity.ok(new ApiResponse<>(200, "Affinity Score 수정 완료",null));
    }



}
