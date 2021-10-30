package com.example.hanium.room.controller;


import com.example.hanium.Auth.model.User;
import com.example.hanium.Auth.repository.UserRepository;
import com.example.hanium.Auth.service.UserService;
import com.example.hanium.chat.dto.ChatDto;
import com.example.hanium.room.dto.RoomDto;
import com.example.hanium.room.model.Room;
import com.example.hanium.room.repository.RoomRepository;
import com.example.hanium.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final UserRepository userRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;

    // userID가 속해있는 모든 방 리턴
    @GetMapping("/rooms")
    public List<RoomDto> getRooms(HttpServletRequest req){
        long fromId = (Long)req.getSession().getAttribute("userId");
        User user = userRepository.findById(fromId).orElseThrow(
                ()->new NullPointerException("접근 오류")
        );
        return roomService.showAllRoom(user);
    }

 // rooomId에 있는 모든 메시지 리턴
   @GetMapping("/message/all/{roomId}")
    public List<ChatDto> getMessages(@PathVariable(name="roomId")Long roomId){
       Room room = roomRepository.findByRoomId(roomId);
       return room.getMessages().stream().map(ChatDto::new).collect(Collectors.toList());
   }

   //roomId로 Room 리턴
@GetMapping("/room/{roomId}")
    public RoomDto getRoom(@PathVariable(name="roomId")Long roomId){
        return new RoomDto(roomRepository.findByRoomId(roomId));
}


}
