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
@Document(collection = "room")
public class Room {
    @Id
    private String id;
    private String name;
    private String password;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime lastMessageAt;
}
