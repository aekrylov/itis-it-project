package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.aekrylov.itproject.entities.Conversation;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.WebHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.ChatService;

import java.util.List;

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
        User currentUser = WebHelpers.getCurrentUser();
        //mapping conversations for legacy view
        List<Conversation> conversations = chatService.getConversations(WebHelpers.getCurrentUser());
        conversations.forEach(conversation -> {
                    if(conversation.getUser2().equals(currentUser)) {
                        conversation.setUser2(conversation.getUser1());
                        conversation.setUser1(currentUser);
                    }
                });
        map.put("conversations", conversations);
        return "messages";
    }
}
