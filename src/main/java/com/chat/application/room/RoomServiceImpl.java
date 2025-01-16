package com.chat.application.room;

import com.chat.application.exception.ChatException;
import com.chat.application.exception.ErrorCode;
import com.chat.application.room.dto.RequestRoomCreate;
import com.chat.domain.chat.Room;
import com.chat.infrastructure.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ReactiveMongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Room> createRoom(RequestRoomCreate request) {
        // HTML 이스케이프 처리
        String sanitizedName = HtmlUtils.htmlEscape(request.name());

        // 비밀번호가 있는 경우에만 해시화
        String encodedPassword = request.password() != null ?
                passwordEncoder.encode(request.password()) : null;

        Room room = Room.builder()
                .name(sanitizedName)
                .password(encodedPassword)
                .lastMessageAt(LocalDateTime.now())
                .build();

        return roomRepository.save(room);
    }

    @Override
    public Mono<Room> findRoom(String roomId) {
        return roomRepository.findByIdAndDeletedAtIsNull(roomId)
                .switchIfEmpty(Mono.error(new ChatException(ErrorCode.ROOM_NOT_FOUND)));
    }

    @Override
    public Flux<Room> findAllRooms() {
        return roomRepository.findAllByDeletedAtIsNull()
                .switchIfEmpty(Mono.error(new ChatException(ErrorCode.ROOM_NOT_FOUND)));
    }

    @Override
    public Mono<Boolean> validatePassword(String roomId, String password) {
        return roomRepository.findByIdAndDeletedAtIsNull(roomId)
                .switchIfEmpty(Mono.error(new ChatException(ErrorCode.ROOM_NOT_FOUND)))
                .handle((room, sink) -> {
                    if (room.getPassword() == null) {
                        sink.next(true);
                        return;
                    }
                    if (!passwordEncoder.matches(password, room.getPassword())) {
                        sink.error(new ChatException(ErrorCode.INVALID_PASSWORD));
                        return;
                    }
                    sink.next(true);
                });
    }

    @Override
    public Mono<Room> updateLastMessageTime(String roomId) {
        Query query = Query.query(Criteria.where("id").is(roomId).and("deletedAt").isNull());
        Update update = Update.update("lastMessageAt", LocalDateTime.now());

        return mongoTemplate.findAndModify(query, update, Room.class)
                .switchIfEmpty(Mono.error(new ChatException(ErrorCode.ROOM_NOT_FOUND)));
    }
}
