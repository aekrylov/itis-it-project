package app.models;

import java.sql.Timestamp;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/3/16 12:57 PM
 */
public class BuySell extends Entity {

    int id;
    User buyer;
    User seller;
    Product product;
    Timestamp date;

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

    public Timestamp getDate() {
        return date;
    }

    public BuySell() {}

    public BuySell(User buyer, User seller, Product product, Timestamp date) {
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
        this.date = date;
    }

    public BuySell(User buyer, User seller, Product product) {
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
    }
}
