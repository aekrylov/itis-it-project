package app.services;

import app.models.*;

import java.sql.SQLException;
import java.sql.SQLWarning;
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

    private Posts posts = new Posts();
    private Products products = new Products();

    private PostService() {
    }

    public boolean createPost(Post post) throws SQLException {
        return posts.create(post);
    }

    public boolean createPost(Map<String, String> map) throws SQLException {
        return posts.create(map);
    }

    public boolean createProduct(Product product) throws SQLException {
        return products.create(product);
    }
    public Product toProduct(Map<String, String> map) throws SQLException {
        return (Product) Entity.getEntity(map, Product.class);
    }

    public Post getPost(int id) throws SQLException {
        return posts.get(id);
    }

    public List<Post> getPost(SimpleFilter filter) throws SQLException {
        return posts.get(filter);
    }
}
