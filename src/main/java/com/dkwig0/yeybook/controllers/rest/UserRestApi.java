package com.dkwig0.yeybook.controllers.rest;

import com.dkwig0.yeybook.exceptions.UserNotFoundException;
import com.dkwig0.yeybook.jpa.entities.User;
import com.dkwig0.yeybook.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRestApi {

    @Autowired
    UserRepository ur;

    @GetMapping
    public List<User> findUser(@RequestParam(name = "like", required = false) String like) {
        if (like != null)
            return ur.findByUsernameLike(like, PageRequest.of(0, 5));
        return ur.findAll();
    }

    @GetMapping("{id}")
    public User userById(@PathVariable Long id) {
        return ur.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("me")
    public User me(Principal principal) {
        return ur.findByUsername(principal.getName());
    }

}
