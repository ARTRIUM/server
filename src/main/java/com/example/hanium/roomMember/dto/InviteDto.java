package com.example.hanium.roomMember.dto;


import java.util.List;
import lombok.Data;

@Data
public class InviteDto {

    private Long roomId;

    private List<Long> friendIdList;

}
