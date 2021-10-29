package com.example.hanium.roomMember.service;

import com.example.hanium.Auth.model.User;
import com.example.hanium.Auth.repository.UserRepository;
import com.example.hanium.friend.model.Friend;
import com.example.hanium.friend.repository.FriendRepository;
import com.example.hanium.room.model.Room;
import com.example.hanium.roomMember.dto.InviteDto;
import com.example.hanium.roomMember.model.RoomMember;
import com.example.hanium.roomMember.repository.RoomMemberRepository;
import com.example.hanium.room.repository.RoomRepository;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomMemberService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final FriendRepository friendRepository;

    // 미팅 시작 버튼
    @Transactional
    public Long createRoom(String roomName,Long userId) {
        User user = userRepository.findByUserId(userId);

        // 새로운 미팅룸 생성 및 저장
        Room room = new Room(roomName);
        roomRepository.save(room);

        // 미팅룸 생성자를 미팅룸에 추가
        joinRoom(user, room);

        return room.getRoomId();
    }

    /* --------------------------------------------------------
       초대하는사람이 이미 방에 있으면 초대가 다시 안되도록 수정하였음
       ( 초대하는사람과 초대받는사람의 친구여부는 클라이언트에서 친구목록만 뜨게하여 초대하기 때문에 지웠음 )
     ------------------------- */

    //친구 초대

    @Transactional
    public void inviteRoom(List<Long> friendList,Long roomId) {

        Room room = roomRepository.findByRoomId(roomId);

        List<Long> userIdListInRoom = room.getRoomMembers().stream()
            .map(o->o.getUser().getUserId())
            .collect(Collectors.toList());


        List<Long> inviteList = friendList.stream().filter(o -> !userIdListInRoom.contains(o))
            .collect(Collectors.toList());

        for(Long inviteUserId : inviteList) {
            User user = userRepository.findByUserId(inviteUserId);
            joinRoom(user,room);
        }

    }

//    // 이메일로 친구 초대
//    @Transactional
//    public boolean inviteRoomByEmail(Long roomId, String friendEmail) {
//        User friend = userRepository.findByEmail(friendEmail);
//        Room room = roomRepository.findByRoomId(roomId);
//
//        if(friend != null){
//            joinRoom(friend,room);
//            return true;
//        }
//        return false;
//    }

    // userID의 특정 미팅룸 삭제
    public Room deleteRoom(Long userId, Long roomId) {
        User user = userRepository.findByUserId(userId);
        Room room = roomRepository.findByRoomId(roomId);
        RoomMember roomMember = roomMemberRepository.findByUserAndRoom(user, room);
        roomMemberRepository.delete(roomMember);

        return room;
    }

    public void joinRoom(User user, Room room) {
        RoomMember roomMember = new RoomMember(user, room);
        roomMemberRepository.save(roomMember);
    }

}