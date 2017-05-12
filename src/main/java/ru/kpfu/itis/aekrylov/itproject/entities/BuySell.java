package ru.kpfu.itis.aekrylov.itproject.entities;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/3/16 12:57 PM
 */
public class BuySell extends Entity {

    int id;
    User buyer;
    User seller;
    Product product;
    Timestamp timestamp;
    Feedback feedback;

    public int getId() {
        return id;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public BuySell() {}

    public BuySell(User buyer, User seller, Product product) {
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
        this.timestamp = Timestamp.from(Instant.now());
    }
}
