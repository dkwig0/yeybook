package com.dkwig0.yeybook.controllers.mvc;

import com.dkwig0.yeybook.jpa.entities.Role;
import com.dkwig0.yeybook.jpa.entities.User;
import com.dkwig0.yeybook.jpa.repositories.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

@Controller
public class ChatController {

    @Autowired
    UserRepository ur;

    @GetMapping
    public String mainChatPage() {
        return "index";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("sound.wav")
    public @ResponseBody byte[] sound() throws Exception {
        return IOUtils.toByteArray(getClass().getResourceAsStream("/static/wav/sound.wav"));
    }

    @GetMapping("registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("registration")
    public String signUserUp(@RequestParam(name = "username", required = true) String username,
                           @RequestParam(name = "password", required = true) String password) {
        ur.save(new User(username, password, true, Collections.singleton(Role.USER)));
        return loginPage();
    }

}
