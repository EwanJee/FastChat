package com.chat.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Document(collection = "room_invite")
public class RoomInvite {
    @Id
    String id;
    String roomId;
    LocalDateTime expiredAt;
    @CreatedDate
    LocalDateTime createdAt;
}
