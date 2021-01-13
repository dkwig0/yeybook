package com.dkwig0.yeybook.controllers.rest;

import com.dkwig0.yeybook.exceptions.ChatRoomNotFoundException;
import com.dkwig0.yeybook.exceptions.UserNotFoundException;
import com.dkwig0.yeybook.jpa.entities.ChatRoom;
import com.dkwig0.yeybook.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users/{userId}/chats")
public class ChatRoomRestApi {

    @Autowired
    UserRepository ur;

    @GetMapping
    public List<ChatRoom> rooms(@PathVariable Long userId) {
        return ur.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)).getChatRooms();
    }

    @GetMapping("{roomId}")
    public ChatRoom roomById(@PathVariable Long userId, @PathVariable Long roomId) {
        return ur.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)).getChatRooms().stream()
                .filter(cr -> cr.getId().equals(roomId)).findAny()
                .orElseThrow(() -> new ChatRoomNotFoundException(userId, roomId));
    }

}
