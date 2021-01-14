package com.dkwig0.yeybook.controllers.rest;

import com.dkwig0.yeybook.exceptions.ChatRoomNotFoundException;
import com.dkwig0.yeybook.jpa.entities.ChatRoom;
import com.dkwig0.yeybook.jpa.repositories.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/chats")
public class ChatRoomRestApi {

    @Autowired
    ChatRoomRepository crr;

    @GetMapping
    public Set<ChatRoom> rooms() {
        return new HashSet<>(crr.findAll());
    }

    @GetMapping("{id}")
    public ChatRoom roomById(@PathVariable Long id) {
        return crr.findById(id).orElseThrow(() -> new ChatRoomNotFoundException(id));
    }

}
