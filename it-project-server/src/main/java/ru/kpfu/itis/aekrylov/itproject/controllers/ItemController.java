package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.WebHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.PostService;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/25/17 10:55 PM
 */

@Controller
@RequestMapping("/item")
public class ItemController {


    private PostService postService;
    private UserService userService;

    @Autowired
    public ItemController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    protected String doGet(@RequestParam("id") Integer id, ModelMap dataModel) throws ServletException, IOException {
        Post post = postService.getPost(id);
        dataModel.put("isSeller", post.getUser().getId() == WebHelpers.getCurrentUser().getId());
        dataModel.put("post", post);
        dataModel.put("product", post.getProduct());

        return "product";

    }

    @PostMapping
    protected String doPost(@RequestParam Map<String, String> map) throws ServletException, IOException {
        String action = map.get("action");

        int postId = Integer.parseInt(map.get("post_id"));

        switch (action) {
            case "sell":
                User buyer = userService.get(map.get("buyer"));
                User seller = WebHelpers.getCurrentUser();

                postService.sellProduct(seller, buyer, postId);
                return "redirect:/items";

            case "delete":
                if (postService.deletePost(postId)) {
                    return "redirect:/items";
                } else {
                    return "redirect:./";
                }
        }

        return "redirect:./";
    }
}
