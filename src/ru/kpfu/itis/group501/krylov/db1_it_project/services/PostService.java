package ru.kpfu.itis.group501.krylov.db1_it_project.services;

import ru.kpfu.itis.group501.krylov.db1_it_project.entities.*;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.NotFoundException;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.DAO;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.misc.SimpleFilter;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 11:49 PM
 */
public class PostService {
    private static PostService ourInstance = new PostService();

    public static PostService getInstance() {
        return ourInstance;
    }

    private DAO<Post> posts = new DAO<>(Post.class);
    private DAO<Product> products = new DAO<>(Product.class);
    private DAO<BuySell> buySells = new DAO<>(BuySell.class);

    private PostService() {
    }

    public boolean createPost(Post post) throws SQLException {
        return posts.create(post);
    }

    public boolean createProduct(Product product) throws SQLException {
        return products.create(product);
    }
    public Product toProduct(Map<String, String> map) throws SQLException {
        return Entity.getEntity(map, Product.class);
    }

    public Post getPost(int id) throws SQLException, NotFoundException {
        return posts.get(id);
    }

    public List<Post> getPosts(SimpleFilter filter) throws SQLException {
        filter.setOrder("timestamp", false);
        return posts.get(filter);
    }

    public boolean sellProduct(User seller, User buyer, int post_id) throws SQLException, NotFoundException {
        Post post = getPost(post_id);
        Product product = post.getProduct();

        BuySell bs = new BuySell(buyer, seller, product);
        return buySells.create(bs) && posts.delete(post_id);
    }

    public boolean deletePost(int post_id) throws SQLException {
        return posts.delete(post_id);
    }

    public int countPosts(User user) throws SQLException {
        return posts.count(new SimpleFilter(posts).addSignClause("user", "=", user.getId()));
    }


}
