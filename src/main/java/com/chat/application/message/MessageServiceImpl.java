package com.chat.application.message;

import com.chat.application.exception.ChatException;
import com.chat.domain.chat.Message;
import com.chat.infrastructure.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Message> createMessage(String roomId, String senderId, String content, Message.Type type) {
        String sanitizedContent = type == Message.Type.MESSAGE ?
                HtmlUtils.htmlEscape(content) : content;

        Message message = Message.builder()
                .roomId(roomId)
                .senderId(senderId)
                .content(sanitizedContent)
                .type(type)
                .build();

        return messageRepository.save(message);
    }

    // 데이터가 없는 것이 반드시 에러 상황은 아니기 때문에 switchIfEmpty 를 사용하지 않음
    @Override
    public Flux<Message> findMessagesByRoomId(String roomId) {
        return messageRepository.findByRoomIdOrderByCreatedAtDesc(roomId);
    }

    @Override
    public Mono<Message> findLatestMessage(String roomId) {
        return messageRepository.findFirstByRoomIdOrderByCreatedAtDesc(roomId);
    }

    @Override
    public Mono<Message> findMessage(String messageId) {
        return messageRepository.findById(messageId);
    }
}
