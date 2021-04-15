package com.dkwig0.yeybook.controllers.rest;

import com.dkwig0.yeybook.exceptions.ChatRoomNotFoundException;
import com.dkwig0.yeybook.jpa.entities.ChatRoom;
import com.dkwig0.yeybook.jpa.entities.Message;
import com.dkwig0.yeybook.jpa.entities.User;
import com.dkwig0.yeybook.jpa.repositories.ChatRoomRepository;
import com.dkwig0.yeybook.jpa.repositories.MessageRepository;
import com.dkwig0.yeybook.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("api/chats")
public class ChatRoomRestApi {

    @Autowired
    ChatRoomRepository crr;

    @Autowired
    MessageRepository mr;

    @Autowired
    UserRepository ur;

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

    @PostMapping
    @Transactional
    public ChatRoom addAFriend(@RequestBody String name, Principal principal) {

        List<User> users = new ArrayList<>();

        users.add(ur.findByUsername(name.replace("=", "")));
        users.add(ur.findByUsername(principal.getName()));

        ChatRoom room = crr.findByUsersContainsAndUsersContains(users.get(0), users.get(1));

        if (room == null) {
            room = crr.save(new ChatRoom(new HashSet<>(users), Collections.emptySet(), false));
            ChatRoom finalRoom = room;
            new HashSet<>(users).forEach(u -> u.getChatRooms().add(finalRoom));
            new HashSet<>(users).forEach(u -> ur.save(u));
        }

        return room;
    }

}
