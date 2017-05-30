package ru.kpfu.itis.aekrylov.itproject.controllers.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;
import ru.kpfu.itis.aekrylov.itproject.forms.FilterSpecForm;
import ru.kpfu.itis.aekrylov.itproject.services.PostService;

import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 5:25 PM
 */

@RestController
@RequestMapping("/ajax/items")
public class ItemsAjaxController {

    private PostService postService;

    @Autowired
    public ItemsAjaxController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public List<Post> doFilterPost(FilterSpecForm form) {
        List<Post> posts = postService.getPosts(form);
        return posts;
    }
}
