package com.chat.application.participant;

import com.chat.domain.chat.Participant;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParticipantService {
    Mono<Participant> joinRoom(String roomId, String nickname, String sessionId);

    Mono<Void> leaveRoom(String sessionId);

    Flux<Participant> findRoomParticipants(String roomId);

    Mono<Participant> updateLastSeenAt(String sessionId);

    Mono<Participant> findBySessionId(String sessionId);

    // 유저의 모든 활성 채팅방 조회
    Flux<Participant> findActiveParticipations(String userId);

    // 재연결 시 세션 업데이트
    Mono<Participant> reconnect(String userId, String newSessionId);
}