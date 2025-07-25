package com.chatting.chatRoom.service;

import com.chatting.affinity.model.Affinity;
import com.chatting.affinity.repository.AffinityRepository;
import com.chatting.chatRoom.model.ChatRoom;
import com.chatting.chatRoom.model.ChatRoomMember;
import com.chatting.chatRoom.repository.ChatRoomMembersRepository;
import com.chatting.chatRoom.repository.ChatRoomRepository;
import com.chatting.global.exception.CustomException;
import com.chatting.global.exception.ErrorCode;
import com.chatting.user.model.Users;
import com.chatting.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final AffinityRepository affinityRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMembersRepository chatRoomMembersRepository;
    private final UsersRepository usersRepository;

    //특허 알고리즘을 활용한 채팅방 생성
    public Long createChatRoomForUser(Long userId) {
        List<Affinity> affinities = affinityRepository.findAll();

        // 상호 하트를 주고 받은 사용자와의 관계 저장
        Map<Long, AffinityPairDetail> mutualMap = new HashMap<>();

        for (Affinity a : affinities) {
            if (!a.getFromUser().getUserId().equals(userId)) continue;

            Long otherId = a.getToUser().getUserId();

            Optional<Affinity> reverse = affinities.stream()
                    .filter(b -> b.getFromUser().getUserId().equals(otherId) &&
                            b.getToUser().getUserId().equals(userId))
                    .findFirst();

            reverse.ifPresent(b -> mutualMap.put(otherId, new AffinityPairDetail(a, b)));
        }

        if (mutualMap.isEmpty()) return 0L;

        // 사건이 1개일 경우
        if (mutualMap.size() == 1) {
            return createRoom(userId, mutualMap.keySet().iterator().next());

        }

        // 사건이 여러 개인 경우: 최고 점수 찾기
        long maxScore = mutualMap.values().stream()
                .mapToLong(p -> p.given().getAffinityScore() + p.received().getAffinityScore())
                .max().orElse(0L);

        List<Map.Entry<Long, AffinityPairDetail>> topEvents = mutualMap.entrySet().stream()
                .filter(e -> e.getValue().given().getAffinityScore() +
                        e.getValue().received().getAffinityScore() == maxScore)
                .toList();

        if (topEvents.size() == 1) {
            return createRoom(userId, topEvents.get(0).getKey());

        }

        // 최고 점수 쌍이 여러 개인 경우
        Map.Entry<Long, AffinityPairDetail> selected = selectBestAmongEquals(userId, topEvents);
        if (selected != null) {
            return createRoom(userId, selected.getKey());
        }

        return 0L;
    }

    private Map.Entry<Long, AffinityPairDetail> selectBestAmongEquals(
            Long userId, List<Map.Entry<Long, AffinityPairDetail>> candidates) {

        // (1) 준 하트 수와 받은 하트 수가 동일한 경우 → 더 많이 준 사람 우선
        List<Map.Entry<Long, AffinityPairDetail>> equalGiveReceive = candidates.stream()
                .filter(e -> e.getValue().given().getAffinityScore()
                        == e.getValue().received().getAffinityScore())
                .toList();

        if (!equalGiveReceive.isEmpty()) {
            return equalGiveReceive.stream()
                    .max(Comparator.comparingLong(e -> e.getValue().given().getAffinityScore()))
                    .orElse(null);
        }

        // (2) 받은/준 하트 수가 다른 경우 → 곱 계산
        Map.Entry<Long, AffinityPairDetail> maxProductEntry = candidates.stream()
                .max(Comparator.comparingLong(e ->
                        (long) e.getValue().given().getAffinityScore()
                                * e.getValue().received().getAffinityScore()))
                .orElse(null);

        // (3) 준 하트 수가 같은 경우 → 먼저 보낸 시간 순
        List<Map.Entry<Long, AffinityPairDetail>> sameGivenList = candidates.stream()
                .collect(Collectors.groupingBy(e -> e.getValue().given().getAffinityScore()))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .flatMap(entry -> entry.getValue().stream())
                .toList();

        return sameGivenList.stream()
                .min(Comparator.comparing(e -> e.getValue().given().getCreatedAt()))
                .orElse(maxProductEntry);
    }

    //채팅방 생성 후 사용자들 매핑
    private Long createRoom(Long user1Id, Long user2Id) {
        Users user1 = usersRepository.findById(user1Id).orElseThrow();
        Users user2 = usersRepository.findById(user2Id).orElseThrow();

        ChatRoom room = chatRoomRepository.save(ChatRoom.builder().build());

        chatRoomMembersRepository.save(ChatRoomMember.of(room, user1));
        chatRoomMembersRepository.save(ChatRoomMember.of(room, user2));
        user1.setIsMatched(room.getChatRoomId());
        user2.setIsMatched(room.getChatRoomId());
        usersRepository.save(user1);
        usersRepository.save(user2);

        return room.getChatRoomId();
    }

    // Affinity 두 개 (userId -> otherId, otherId -> userId) 쌍을 하나로 묶는 record
    private record AffinityPairDetail(Affinity given, Affinity received) {}

    public Long getChatRoomId(Long userId) {
        ChatRoomMember chatRoomMember = chatRoomMembersRepository.findByUserUserId(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomMember.getChatRoom().getChatRoomId())
                .orElseThrow(()-> new CustomException(ErrorCode.DATA_NOT_FOUND));

        return chatRoom.getChatRoomId();

    }
}
