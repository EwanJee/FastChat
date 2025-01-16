package com.chat.infrastructure;

import com.chat.domain.chat.Participant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ParticipantRepository extends ReactiveMongoRepository<Participant, String> {
    Flux<Participant> findByRoomId(String roomId);

    Mono<Participant> findByRoomIdAndSessionId(String roomId, String sessionId);

    Mono<Void> deleteBySessionId(String sessionId);

    Flux<Participant> findAllByRoomIdAndDeletedAtIsNull(String roomId);

    Mono<Participant> findBySessionIdAndDeletedAtIsNull(String sessionId);

    Flux<Participant> findAllBySessionIdAndDeletedAtIsNull(String sessionId);
}