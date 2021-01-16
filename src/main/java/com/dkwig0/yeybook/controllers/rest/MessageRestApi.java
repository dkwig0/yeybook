package com.dkwig0.yeybook.controllers.rest;

import com.dkwig0.yeybook.exceptions.ChatRoomNotFoundException;
import com.dkwig0.yeybook.exceptions.MessageNotFoundException;
import com.dkwig0.yeybook.jpa.entities.ChatRoom;
import com.dkwig0.yeybook.jpa.entities.Message;
import com.dkwig0.yeybook.jpa.repositories.ChatRoomRepository;
import com.dkwig0.yeybook.jpa.repositories.MessageRepository;
import com.dkwig0.yeybook.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/messages")
public class MessageRestApi {

    @Autowired
    private MessageRepository mr;

    @Autowired
    private UserRepository ur;

    @Autowired
    private ChatRoomRepository crr;

    @GetMapping
    public Set<Message> messages() {
        return new HashSet<>(mr.findAll());
    }

    @GetMapping("{id}")
    public Message messageById(@PathVariable Long id) {
        return mr.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message, Principal principal) {
        Long roomId = message.getChatRoom().getId();
        ChatRoom cr = crr.findById(roomId).orElseThrow(() -> new ChatRoomNotFoundException(roomId));
        message.setChatRoom(cr);
        message.setDate(LocalDateTime.now());
        message.setUser(ur.findByUsername(principal.getName()));
        return mr.save(message);
    }

}
