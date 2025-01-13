package com.chat.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "participant")
public class Participant {
    @Id
    private String id;
    private String roomId;
    private String nickname;
    private String sessionId; // WebSocket 세션 ID
    @CreatedDate
    private LocalDateTime createdAt; // 입장 시간
    private LocalDateTime lastSeenAt; // 마지막으로 메시지를 확인한 시간
}
