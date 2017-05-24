package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.aekrylov.itproject.entities.Message;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.ChatService;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/24/17 1:36 PM
 */

@Controller
@RequestMapping({"/user/chat", "/user/chat/{uid}"})
public class ChatController {

    private ChatService chatService;
    private UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping
    public String doPost(@PathVariable("uid") int to, @RequestParam("text") String text) {
        chatService.sendMessage(CommonHelpers.getCurrentUser(), to, text);
        return "redirect:/user/chat/"+to;
    }


    @GetMapping
    public String doGet(@PathVariable("uid") int to, ModelMap modelMap) {
        User thisUser = CommonHelpers.getCurrentUser();
        User thatUser = userService.get(to);

        List<Message> messages = chatService.getConversation(thisUser, thatUser);

        modelMap.put("user1", thisUser);
        modelMap.put("user2", thatUser);
        modelMap.put("messages", messages);

        //todo call this after the request?
        chatService.markRead(thisUser, thatUser);

        return "dialog";
    }
}
