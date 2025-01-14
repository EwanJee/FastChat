package com.chat.infrastructure;

import com.chat.domain.chat.File;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FileRepository extends ReactiveMongoRepository<File, String> {
    Flux<File> findByRoomId(String roomId);

    Flux<File> findByMessageId(String messageId);
}
