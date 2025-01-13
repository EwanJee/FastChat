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
@Document(collection = "message")
public class Message {
    @Id
    private String id;
    private String roomId;
    private String senderId; // 발신자 ID
    private String content;
    private Type type; // 메시지 타입
    @CreatedDate
    private LocalDateTime createdAt;

    public enum Type {
        JOIN, LEAVE, MESSAGE, FILE
    }
}
