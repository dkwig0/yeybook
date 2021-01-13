package com.dkwig0.yeybook.controllers.rest;

import com.dkwig0.yeybook.exceptions.UserNotFoundException;
import com.dkwig0.yeybook.jpa.entities.User;
import com.dkwig0.yeybook.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRestApi {

    @Autowired
    UserRepository ur;

    @GetMapping
    public List<User> users() {
        return ur.findAll();
    }

    @GetMapping("{id}")
    public User userById(@PathVariable Long id) {
        return ur.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

}
