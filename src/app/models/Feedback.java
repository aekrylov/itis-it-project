package app.models;

import java.sql.Timestamp;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:26 PM
 */
public class Feedback extends Entity {

    int id;
    BuySell buy_sell;
    String comment;
    Timestamp date;
    int score;

    public int getId() {
        return id;
    }
    public String getComment() {
        return comment;
    }
    public Timestamp getDate() {
        return date;
    }
    public int getScore() {
        return score;
    }
    public BuySell getBuy_sell() {
        return buy_sell;
    }

    public Feedback() {}

    public Feedback(BuySell buy_sell, String comment, Timestamp date, int score) {
        this.buy_sell = buy_sell;
        this.comment = comment;
        this.date = date;
        this.score = score;
    }

    public Feedback(BuySell buy_sell, String comment, int score) {
        this.buy_sell = buy_sell;
        this.comment = comment;
        this.score = score;
    }
}
