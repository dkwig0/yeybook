package com.dkwig0.yeybook.ws;

import com.dkwig0.yeybook.jpa.entities.Message;
import com.dkwig0.yeybook.jpa.repositories.ChatRoomRepository;
import com.dkwig0.yeybook.jpa.repositories.MessageRepository;
import com.dkwig0.yeybook.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {

    @Autowired
    ChatRoomRepository crr;

    @Autowired
    MessageRepository mr;

    @Autowired
    UserRepository ur;

    @MessageMapping("/{chatId}")
    @SendTo("/topic/{chatId}")
    public Message sendMessage(Message message, @DestinationVariable("chatId") Long id, Principal principal) {
        message.setUser(ur.findByUsername(principal.getName()));
        message.setChatRoom(crr.findById(id).get());
//        message.setChatRoom(crr.findById(id).get());
        return message;
    }

}
