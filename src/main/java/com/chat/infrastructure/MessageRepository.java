package com.chat.infrastructure;

import com.chat.domain.chat.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
    Flux<Message> findByRoomIdOrderByCreatedAtDesc(String roomId);

    Mono<Message> findFirstByRoomIdOrderByCreatedAtDesc(String roomId);
}
