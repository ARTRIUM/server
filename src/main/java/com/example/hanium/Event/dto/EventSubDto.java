package com.example.hanium.Event.dto;

import lombok.Data;

@Data
public class EventSubDto {

    private String roomName;
    private Long roomId;

    public EventSubDto(String roomName,Long roomId){
        this.roomName=roomName;
        this.roomId=roomId;
    }
}
