package com.dkwig0.yeybook.controllers.rest;

import com.dkwig0.yeybook.exceptions.ChatRoomNotFoundException;
import com.dkwig0.yeybook.jpa.entities.ChatRoom;
import com.dkwig0.yeybook.jpa.entities.Message;
import com.dkwig0.yeybook.jpa.repositories.ChatRoomRepository;
import com.dkwig0.yeybook.jpa.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/chats")
public class ChatRoomRestApi {

    @Autowired
    ChatRoomRepository crr;

    @Autowired
    MessageRepository mr;

    @GetMapping
    public Set<ChatRoom> rooms() {
        return new HashSet<>(crr.findAll());
    }

    @GetMapping("{id}")
    public ChatRoom roomById(@PathVariable Long id) {
        return crr.findById(id).orElseThrow(() -> new ChatRoomNotFoundException(id));
    }

    @GetMapping("{id}/messages")
    public List<Message> messageList(@PathVariable(name = "id") Long id,
                                     @RequestParam(name = "page", required = false) Long page,
                                     @RequestParam(name = "size", required = false) Long size) {
        return mr.findByChatRoomIdOrderByDateDesc(id, PageRequest.of(page.intValue(), size.intValue()));
    }

}
