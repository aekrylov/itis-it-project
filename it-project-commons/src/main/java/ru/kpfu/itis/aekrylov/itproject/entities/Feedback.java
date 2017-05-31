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
    private Integer id;

    @OneToOne(mappedBy = "feedback")
    private BuySell buySell;

    private String comment;
    private Timestamp date;
    private int score;

    public Integer getId() {
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

    public BuySell getBuySell() {
        return buySell;
    }

    public void setBuySell(BuySell buySell) {
        this.buySell = buySell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;

        Feedback feedback = (Feedback) o;

        if (getScore() != feedback.getScore()) return false;
        if (getId() != null ? !getId().equals(feedback.getId()) : feedback.getId() != null) return false;
        return getBuySell().equals(feedback.getBuySell());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getBuySell().hashCode();
        result = 31 * result + getScore();
        return result;
    }
}
