package com.example.hanium.chat.controller;

import com.example.hanium.Auth.model.User;
import com.example.hanium.Auth.repository.UserRepository;
import com.example.hanium.chat.dto.ChatDto;
import com.example.hanium.chat.model.Chat;
import com.example.hanium.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @MessageMapping("/chat/{userId}/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")
    public ChatDto send(@DestinationVariable("userId") Long userId, @DestinationVariable("roomId") Long roomId, @Payload String content) throws Exception{

        String ncontent=content.substring(0,content.length()-2);
        Chat chat = chatService.createChat(userId, roomId, ncontent);
        chatService.chatSave(chat);

        log.info("받은 메세지 : {}",content);

        return new ChatDto(chat);

    }

//    @MessageMapping(value = "/chat/enter")
//    public void enter(ChatDto message) {
//        message.setMessage(message.getName() + "님이 미팅에 참여하였습니다.");
//        template.convertAndSend("/sub/chat/room" + message.getRoomId(), message);
//    }
//
//    @MessageMapping("/chat/message")
//    public void message(ChatDto message) {
//        System.out.println("message  = " + message);
//            template.convertAndSend("/topic/room/" + message.getRoomId(), message);
//    }
}
