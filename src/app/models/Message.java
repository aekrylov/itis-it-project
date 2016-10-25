package app.models;

import java.sql.Date;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 9:06 PM
 */
public class Message {
    private int id;
    private User from;
    private User to;

    private String text;
    private Date date;

    private boolean read;

    public Message(int id, User from, User to, String text, Date date, boolean read) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.text = text;
        this.date = date;
        this.read = read;
    }

    public User getFrom() {
        return from;
    }
    public User getTo() {
        return to;
    }
    public String getText() {
        return text;
    }
    public Date getDate() {
        return date;
    }
    public int getId() {
        return id;
    }
    public boolean isRead() {
        return read;
    }
}
