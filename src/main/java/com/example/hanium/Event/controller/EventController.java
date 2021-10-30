package com.example.hanium.Event.controller;


import com.example.hanium.Event.dto.EventSubDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class EventController {


    @MessageMapping("/event/sub/{toId}")
    @SendTo("/sub/{toId}")
    public EventSubDto greeting(@DestinationVariable("toId") Long toId,@Payload EventSubDto event) throws Exception {
        Thread.sleep(100); // delay
        return event;
    }
}
