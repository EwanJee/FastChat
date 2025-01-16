package com.chat.application.participant;

import com.chat.application.exception.ChatException;
import com.chat.application.exception.ErrorCode;
import com.chat.domain.chat.Participant;
import com.chat.infrastructure.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Participant> joinRoom(String roomId, String nickname, String sessionId) {
        String sanitizedNickname = HtmlUtils.htmlEscape(nickname);

        Participant participant = Participant.builder()
                .roomId(roomId)
                .nickname(sanitizedNickname)
                .sessionId(sessionId)
                .lastSeenAt(LocalDateTime.now())
                .build();

        return participantRepository.save(participant);
    }

    @Override
    public Mono<Void> leaveRoom(String sessionId) {
        Query query = Query.query(Criteria.where("sessionId").is(sessionId).and("deletedAt").isNull());
        Update update = Update.update("deletedAt", LocalDateTime.now());

        return mongoTemplate.findAndModify(query, update, Participant.class)
                .switchIfEmpty(Mono.error(new ChatException(ErrorCode.PARTICIPANT_NOT_FOUND))).then();
    }

    @Override
    public Flux<Participant> findRoomParticipants(String roomId) {
        return participantRepository.findAllByRoomIdAndDeletedAtIsNull(roomId)
                .switchIfEmpty(Mono.error(new ChatException(ErrorCode.PARTICIPANT_NOT_FOUND)));
    }

    @Override
    public Mono<Participant> updateLastSeenAt(String sessionId) {
        Query query = Query.query(Criteria.where("sessionId").is(sessionId).and("deletedAt").isNull());
        Update update = Update.update("lastSeenAt", LocalDateTime.now());

        return mongoTemplate.findAndModify(query, update, Participant.class)
                .switchIfEmpty(Mono.error(new ChatException(ErrorCode.PARTICIPANT_NOT_FOUND)));
    }

    @Override
    public Mono<Participant> findBySessionId(String sessionId) {
        return participantRepository.findBySessionIdAndDeletedAtIsNull(sessionId)
                .switchIfEmpty(Mono.error(new ChatException(ErrorCode.PARTICIPANT_NOT_FOUND)));
    }

    @Override
    public Flux<Participant> findActiveParticipations(String sessionId) {
        return participantRepository.findAllBySessionIdAndDeletedAtIsNull(sessionId);
    }

    @Override
    public Mono<Participant> reconnect(String sessionId, String newSessionId) {
        Query query = Query.query(Criteria.where("sessionId").is(sessionId)
                .and("deletedAt").isNull());
        Update update = Update.update("sessionId", newSessionId)
                .set("lastSeenAt", LocalDateTime.now());

        return mongoTemplate.findAndModify(query, update, Participant.class);
    }
}
