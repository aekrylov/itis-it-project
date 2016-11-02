package app.models;

import java.sql.Date;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:26 PM
 */
public class Feedback extends Entity {

    private int id;
    private User author;
    private String comment;
    private Date date;
    private int score;

    public int getId() {
        return id;
    }
    public User getAuthor() {
        return author;
    }
    public String getComment() {
        return comment;
    }
    public Date getDate() {
        return date;
    }
    public int getScore() {
        return score;
    }

    public Feedback(int id, User author, String comment, Date date, int score) {
        this.id = id;
        this.author = author;
        this.comment = comment;
        this.date = date;
        this.score = score;
    }
}
