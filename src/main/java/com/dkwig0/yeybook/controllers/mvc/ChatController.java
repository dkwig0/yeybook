package com.dkwig0.yeybook.controllers.mvc;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("sound.wav")
    public @ResponseBody byte[] sound() throws Exception {
        return IOUtils.toByteArray(getClass().getResourceAsStream("/static/wav/sound.wav"));
    }

}
