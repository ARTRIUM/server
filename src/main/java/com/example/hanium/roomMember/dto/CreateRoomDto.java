package com.example.hanium.roomMember.dto;

import java.util.List;
import lombok.Data;


@Data
public class CreateRoomDto {

    private List<Long> friendIdList;

    private String roomName;
}
