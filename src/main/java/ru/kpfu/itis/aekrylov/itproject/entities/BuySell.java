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
public class BuySell {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User buyer;

    @ManyToOne
    private User seller;

    @ManyToOne
    private Product product;

    private Timestamp timestamp;

    @OneToOne
    private Feedback feedback;

    public BuySell() {
    }

    public BuySell(User buyer, User seller, Product product) {
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
        this.timestamp = Timestamp.from(Instant.now());
    }

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
}
