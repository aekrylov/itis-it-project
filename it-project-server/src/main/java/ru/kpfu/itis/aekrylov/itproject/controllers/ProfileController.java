package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.FeedbackService;
import ru.kpfu.itis.aekrylov.itproject.services.PostService;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/25/17 10:10 PM
 */

@Controller
@RequestMapping("/user")
public class ProfileController {

    private UserService userService;
    private PostService postService;
    private FeedbackService feedbackService;

    public ProfileController(UserService userService, PostService postService, FeedbackService feedbackService) {
        this.userService = userService;
        this.postService = postService;
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public String doGet(@RequestParam(value = "id", required = false) Integer id, ModelMap dataModel) {
        User user;
        if(id == null) {
            user = CommonHelpers.getCurrentUser();
        } else {
            user = userService.get(id);
        }

        dataModel.put("post_count", postService.countPosts(user));
        dataModel.put("buy_sells", feedbackService.getRecentFeedbacks(user));
        dataModel.put("user", user);

        if(user.equals(CommonHelpers.getCurrentUser())) {
            dataModel.put("owner", true);
        }

        return "profile";
    }

    @PostMapping
    protected String doPost(@RequestPart("image") MultipartFile image) throws IOException {
        User user = CommonHelpers.getCurrentUser();
        CommonHelpers.saveImage(Paths.get("users", String.valueOf(user.getId())), image);
        user.setHas_avatar(true);
        userService.updateInfo(user);
        return "redirect:/user";
    }
}
