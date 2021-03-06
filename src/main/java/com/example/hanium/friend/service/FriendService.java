package com.example.hanium.friend.service;

import com.example.hanium.Auth.dto.AddFriendSuccessDto;
import com.example.hanium.Auth.model.User;
import com.example.hanium.Auth.repository.UserRepository;
import com.example.hanium.friend.dto.FriendDto;
import com.example.hanium.friend.model.Friend;
import com.example.hanium.friend.repository.FriendRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    //친구 추가
    @Transactional
    public AddFriendSuccessDto addFriend(Long myId, String friendEmail) {
         User me = userRepository.findById(myId).orElseThrow(
                () -> new NullPointerException("접근 오류")
        );
        User newfriend = userRepository.findByEmail(friendEmail);
        if (me == newfriend) return new AddFriendSuccessDto(false, ""); // 본인을 친구로 등록한 경우

        //친구 중복 등록 방지
        boolean check = me.getFriends().stream()
                .anyMatch(a->a.getFriend().getUserId() == newfriend.getUserId());

        if(check){
            return new AddFriendSuccessDto(false, "");
        }
        //친구가 유저인 경우 등록
        if (newfriend != null) {
            Friend friend = Friend.createFriendShip(me, newfriend);
            friendRepository.save(friend);
            return new AddFriendSuccessDto(true, newfriend.getUsername());
        }
        return new AddFriendSuccessDto(false, "");
    }
    //친구 삭제
    @Transactional
    public String deleteFriend(long myId, long friendId){
        if(myId == friendId) return "본인을 삭제할 수 없습니다";

        User me = userRepository.findById(myId).orElseThrow(
                () -> new NullPointerException("접근 오류")
        );
        Optional<Friend> first = me.getFriends().stream()
                .filter(a -> a.getFriend().getUserId()==friendId)
                .findFirst();

        if(first.isPresent()){
            Friend friend = first.get();
            friendRepository.deleteById(friend.getId());
            return "친구 삭제 완료";
        }
        return "친구 삭제 오류";
    }


    @Transactional
    public List<FriendDto> getAllFriend(Long userId){
        List<Friend> friendList = friendRepository.findAllByUserId(userId)
            .orElseThrow(() -> new NullPointerException("친구가 존재하지 않습니다"));

        return friendList.stream()
            .map(o->new FriendDto(o))
            .collect(Collectors.toList());
    }
}