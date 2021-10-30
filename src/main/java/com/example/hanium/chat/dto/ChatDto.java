package com.example.hanium.chat.dto;

import com.example.hanium.Auth.model.UserLanguage;
import com.example.hanium.chat.model.Chat;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChatDto {
    private Long messageId;
    private Long roomId;
    private Long userId;
    private String content;     //메세지
    private UserLanguage language;    //사용언어
    private String writtenAt;
    private String writtenBy;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ChatDto(Chat chat) {
        this.messageId=chat.getChatID();
        this.roomId = chat.getRoom().getRoomId();
        this.userId = chat.getUser().getUserId();
        this.content = chat.getMessage();
        this.language = chat.getLanguage();
        this.writtenAt=chat.getWrittenAt().format(formatter);
        this.writtenBy=chat.getUser().getUsername();
    }
}
