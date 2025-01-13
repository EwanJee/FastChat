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
@Document(collection = "file")
public class File {
    @Id
    private String id;
    private String roomId;
    private String messageId; // 파일이 속한 메시지
    private String fileName;
    private String fileType; // MIME TYPE
    private long fileSize;
    private String fileUrl;
    private String uploaderID;
    @CreatedDate
    private LocalDateTime createdAt;
}
