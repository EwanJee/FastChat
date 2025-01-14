package com.chat.infrastructure;

import com.chat.domain.chat.Room;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RoomRepository extends ReactiveMongoRepository<Room, String> {
    Mono<Room> findByIdAndLastMessageAtNotNull(String id);

    Mono<Room> findByIdAndDeletedAtIsNull(String id);

    Flux<Room> findAllByDeletedAtIsNull();
}