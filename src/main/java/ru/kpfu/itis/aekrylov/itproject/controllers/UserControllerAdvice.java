package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.ChatService;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 7:31 PM
 */
@ControllerAdvice(basePackageClasses = MainController.class)
public class UserControllerAdvice {

    private ChatService chatService;

    @Autowired
    public UserControllerAdvice(ChatService chatService) {
        this.chatService = chatService;
    }

    @ModelAttribute("current_user")
    public User currentUser() {
        User user = CommonHelpers.getCurrentUser();
        return user;
    }

    @ModelAttribute("unread_count")
    public int unreadCount() {
        return chatService.getUnreadCount(CommonHelpers.getCurrentUser());
    }
}
