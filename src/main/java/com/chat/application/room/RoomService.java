package com.chat.application.room;

import com.chat.application.room.dto.RequestRoomCreate;
import com.chat.domain.chat.Room;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoomService {

    Mono<Room> createRoom(RequestRoomCreate request);

    Mono<Room> findRoom(String roomId);

    Flux<Room> findAllRooms();

    Mono<Boolean> validatePassword(String roomId, String password);

    Mono<Room> updateLastMessageTime(String roomId);
}
