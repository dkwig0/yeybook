package com.dkwig0.yeybook.controllers.rest;

import com.dkwig0.yeybook.exceptions.MessageNotFoundException;
import com.dkwig0.yeybook.jpa.entities.Message;
import com.dkwig0.yeybook.jpa.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/messages")
public class MessageRestApi {

    @Autowired
    private MessageRepository mr;

    @GetMapping
    public Set<Message> messages() {
        return new HashSet<>(mr.findAll());
    }

    @GetMapping("{id}")
    public Message messageById(@PathVariable Long id) {
        return mr.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }

}
