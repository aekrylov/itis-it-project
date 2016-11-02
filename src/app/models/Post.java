package app.models;

import java.sql.Timestamp;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/1/16 10:29 PM
 */
public class Post extends Entity {
    private int id;
    private Product product;
    private User user;
    private Timestamp timestamp;

    public Post(){}

    public Post(int id, Product product, User user, Timestamp timestamp) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Post(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public int getId() {
        return id;
    }
    public Product getProduct() {
        return product;
    }
    public User getUser() {
        return user;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
