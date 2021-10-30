package com.example.hanium.roomMember.controller;

import com.example.hanium.Auth.service.UserService;
import com.example.hanium.room.dto.RoomDto;
import com.example.hanium.room.model.Room;
import com.example.hanium.roomMember.dto.CreateRoomDto;
import com.example.hanium.roomMember.dto.InviteDto;
import com.example.hanium.roomMember.service.RoomMemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.hanium.utils.ApiUtils.ApiResult;

import static com.example.hanium.utils.ApiUtils.success;

@RequiredArgsConstructor
@RestController
public class RoomMemberController {

    final private RoomMemberService roomMemberService;
    final private UserService userService;


    // 미팅 시작 버튼 -> 친구선택 -> 미팅룸 생성
    @PostMapping("/room/create")
    public Long createRoom(@RequestBody CreateRoomDto createRoomDto, HttpServletRequest req) {
        Long userId =userService.getUserId(req.getSession());
        Long roomId = roomMemberService.createRoom(createRoomDto.getRoomName(),userId);
        roomMemberService.inviteRoom(createRoomDto.getFriendIdList(),roomId);

        return roomId;
    }

    // 방에서 친구 추가 초대
    @PostMapping("/room/invite")
    public ApiResult<String> inviteRoom(@RequestBody InviteDto inviteDto) {
        roomMemberService.inviteRoom(inviteDto.getFriendIdList(),inviteDto.getRoomId());

        return success("성공적으로 초대하였습니다.");
    }

    // userID의 특정 미팅룸 삭제
    @DeleteMapping("/room/{userId}/{roomId}")
    public Room deleteRoom(@PathVariable("userId") Long userId, @PathVariable("roomId") Long roomId) {
        return roomMemberService.deleteRoom(userId, roomId);
    }
}
