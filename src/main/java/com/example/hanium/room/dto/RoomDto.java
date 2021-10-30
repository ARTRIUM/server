package com.example.hanium.room.dto;


import com.example.hanium.chat.model.Chat;
import com.example.hanium.room.model.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RoomDto {
    Long roomId;
    String roomName;
    String recentContent;
    String recentTime;

    public RoomDto(Long roomId, String roomName, String recentContent, String recentTime) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.recentContent = recentContent;
        this.recentTime = recentTime;
    }

    public RoomDto(Room room){
        this.roomId=room.getRoomId();
        this.roomName=room.getRoomName();
        this.recentContent="";
        this.recentTime="";

        if(room.getMessages().size()!=0){
            this.recentContent = room.getMessages().get(room.getMessages().size() - 1).getMessage();
            this.recentTime=room.getMessages().get(room.getMessages().size()-1).getWrittenAt().toString();
        }
    }
}
