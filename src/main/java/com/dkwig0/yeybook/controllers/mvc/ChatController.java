package com.dkwig0.yeybook.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping
    public String mainChatPage() {
        return "index";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

}
