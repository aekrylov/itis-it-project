package ru.kpfu.itis.aekrylov.itproject.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/3/16 12:57 PM
 */
@javax.persistence.Entity
@Table(name = "buy_sells")
public class BuySell extends Entity {

    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User buyer;

    @ManyToOne
    User seller;

    @ManyToOne
    Product product;

    Timestamp timestamp;

    @OneToOne
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
