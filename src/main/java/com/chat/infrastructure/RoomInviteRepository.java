package com.chat.infrastructure;

import com.chat.domain.chat.RoomInvite;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface RoomInviteRepository extends ReactiveMongoRepository<RoomInvite, String> {
    Flux<RoomInvite> findByRoomIdAndExpiresAtAfter(String roomId, LocalDateTime now);
}
