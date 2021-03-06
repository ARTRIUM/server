package com.example.hanium.friend.controller;

import com.example.hanium.Auth.dto.AddFriendSuccessDto;
import com.example.hanium.Auth.model.User;
import com.example.hanium.Auth.repository.UserRepository;
import com.example.hanium.friend.dto.EmailDto;
import com.example.hanium.friend.dto.FriendDto;
import com.example.hanium.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final UserRepository userRepository;


    //친구 추가
    @PostMapping("/friend")
    public AddFriendSuccessDto addFriend(@RequestBody EmailDto email, HttpServletRequest req){
        System.out.println(email.getEmail());
        Long myId = (Long) req.getSession().getAttribute("userId");
        System.out.println(myId);
        return friendService.addFriend(myId, email.getEmail());
    }

    // 친구 리스트
    @GetMapping("/friends")
    public List<FriendDto> allFriends(HttpServletRequest req){
        Long myId = (Long) req.getSession().getAttribute("userId");
        User user = userRepository.findById(myId).orElseThrow(
                ()->new NullPointerException("접근 오류")
        );
        return user.getFriends().stream()
                .map(o -> new FriendDto(o))
                .collect(Collectors.toList());
    }

    //친구 삭제
    @GetMapping("/friend/{myId}/{friendId}")
    public String deleteFriend(@PathVariable Long myId, @PathVariable Long friendId){
        return friendService.deleteFriend(myId,friendId);
    }

}
