package ru.kpfu.itis.aekrylov.itproject.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:26 PM
 */
@javax.persistence.Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
            @GeneratedValue
    int id;

    @OneToOne(mappedBy = "feedback")
    private BuySell buySell;

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

    public Feedback() {
        date = Timestamp.from(Instant.now());
    }

    public Feedback(String comment, Timestamp date, int score) {
        this.comment = comment;
        this.date = date;
        this.score = score;
    }

    public Feedback(String comment, int score) {
        this();
        this.comment = comment;
        this.score = score;
    }
}
