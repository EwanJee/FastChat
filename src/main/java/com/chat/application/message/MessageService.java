package com.chat.application.message;

import com.chat.domain.chat.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
    Mono<Message> createMessage(String roomId, String senderId, String content, Message.Type type);

    Flux<Message> findMessagesByRoomId(String roomId);

    Mono<Message> findLatestMessage(String roomId);

    Mono<Message> findMessage(String messageId);
}
