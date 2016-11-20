package ru.kpfu.itis.group501.krylov.db1_it_project.entities;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/1/16 10:29 PM
 */
public class Post extends Entity {
    int id;
    Product product;
    User user;
    Timestamp timestamp;

    public Post(){
        timestamp = Timestamp.from(Instant.now());
    }

    public Post(int id, Product product, User user, Timestamp timestamp) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Post(Product product, User user) {
        this();
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
