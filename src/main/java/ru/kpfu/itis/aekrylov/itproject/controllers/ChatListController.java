package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.ChatService;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/24/17 12:56 PM
 */

@Controller
@RequestMapping("/user/chats")
public class ChatListController {

    private ChatService chatService;

    @Autowired
    public ChatListController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public String doGet(ModelMap map) {
        //todo pagination
        map.put("conversations", chatService.getConversations(CommonHelpers.getCurrentUser()));
        return "messages";
    }
}
