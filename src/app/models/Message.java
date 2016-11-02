package app.models;

import java.sql.Timestamp;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 9:06 PM
 */
public class Message extends Entity {
    private int id;
    private User from;
    private User to;

    private String text;
    private Timestamp date;

    private boolean read = false;

    public Message(User from, User to, String text, Timestamp timestamp) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.date = timestamp;
    }

    public Message(int id, User from, User to, String text, Timestamp date, boolean read) {
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
    public Timestamp getTimestamp() {
        return date;
    }
    public int getId() {
        return id;
    }
    public boolean isRead() {
        return read;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
}
