package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.aekrylov.itproject.entities.*;
import ru.kpfu.itis.aekrylov.itproject.misc.NotFoundException;
import ru.kpfu.itis.aekrylov.itproject.models.misc.SimpleFilter;
import ru.kpfu.itis.aekrylov.itproject.repositories.BuySellRepository;
import ru.kpfu.itis.aekrylov.itproject.repositories.PostRepository;
import ru.kpfu.itis.aekrylov.itproject.repositories.ProductRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 11:49 PM
 */
@Service
public class PostService {
    private PostRepository postRepository;
    private ProductRepository productRepository;
    private BuySellRepository buySellRepository;

    @Autowired
    public PostService(PostRepository postRepository, ProductRepository productRepository, BuySellRepository buySellRepository) {
        this.postRepository = postRepository;
        this.productRepository = productRepository;
        this.buySellRepository = buySellRepository;
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public Post getPost(int id) {
        return postRepository.findOne(id);
    }

    public List<Post> getPosts(SimpleFilter filter) {  //todo
        filter.setOrder("timestamp", false);
        throw new RuntimeException("not implemented");
    }

    public void sellProduct(User seller, User buyer, int post_id) {
        Post post = getPost(post_id);
        Product product = post.getProduct();

        BuySell bs = new BuySell(buyer, seller, product);
        buySellRepository.save(bs);
        postRepository.delete(post_id);
    }

    public boolean deletePost(int post_id) {
        postRepository.delete(post_id);
        return true;
    }

    public int countPosts(User user) {
        return postRepository.countAllByUser(user);
    }


}
