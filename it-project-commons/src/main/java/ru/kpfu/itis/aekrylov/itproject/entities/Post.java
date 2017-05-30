package ru.kpfu.itis.aekrylov.itproject.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/1/16 10:29 PM
 */
@javax.persistence.Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    private
    int id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private User user;

    private Timestamp timestamp = Timestamp.from(Instant.now());

    public Post() {
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
