package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;
import ru.kpfu.itis.aekrylov.itproject.forms.FilterSpecForm;
import ru.kpfu.itis.aekrylov.itproject.services.PostService;

import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/25/17 11:01 PM
 */

@Controller
@RequestMapping("/items")
public class ItemsController {

    private PostService postService;

    @Autowired
    public ItemsController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    protected String doGet(FilterSpecForm form, @RequestParam Map<String, String> map, ModelMap dataModel) {

        if(map.get("filtered") != null) {
            dataModel.put("filtered", true);
        }
        if(map.get("author") != null) {
            dataModel.put("filtered", true);
        }

        List<Post> posts = postService.getPosts(form);
        dataModel.put("posts", posts);
        dataModel.put("params", form);

        return "product_list";

    }
}
