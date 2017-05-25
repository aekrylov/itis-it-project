package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;
import ru.kpfu.itis.aekrylov.itproject.forms.FilterForm;
import ru.kpfu.itis.aekrylov.itproject.models.misc.SimpleFilter;
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
    protected String doGet(@RequestParam FilterForm form, @RequestParam Map<String, String> map, ModelMap dataModel) {
        SimpleFilter filter = new SimpleFilter();

        if(map.get("filtered") != null) {
            //todo more filters
            dataModel.put("filtered", true);
            filter.addSignClause("type", "=", form.getType());
            filter.addLikeClause("brand", form.getBrand());
            filter.addLikeClause("model", form.getModel());
            filter.addSignClause("price", ">", form.getPrice_low());
            filter.addSignClause("price", "<", form.getPrice_high());
            filter.addSignClause("cores", "=", form.getCores());
        }
        if(map.get("author") != null) {
            dataModel.put("filtered", true);
            filter.addSignClause("user", "=", form.getAuthor());
        }

        List<Post> posts = postService.getPosts(filter);
        dataModel.put("posts", posts);
        dataModel.put("params", map);

        return "product_list";

    }
}
