package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;
import ru.kpfu.itis.aekrylov.itproject.entities.Product;
import ru.kpfu.itis.aekrylov.itproject.misc.WebHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.PostService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/25/17 10:48 PM
 */
@Controller
@PreAuthorize("isFullyAuthenticated()")
@RequestMapping("/item/add")
public class ItemAddController {


    private PostService postService;

    @Autowired
    public ItemAddController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    protected String doGet() throws ServletException, IOException {
        return "new_product";
    }

    @PostMapping
    protected String doPost(Product product, @RequestParam("image")MultipartFile image) throws IOException {
        //add product and redirect to its page
        postService.createProduct(product);
        Post post = new Post(product, WebHelpers.getCurrentUser());
        postService.createPost(post);

        WebHelpers.saveImage(Paths.get("products", String.valueOf(product.getId()), "1"),
                image);

        return "redirect:/item?id="+post.getId();
    }
}
