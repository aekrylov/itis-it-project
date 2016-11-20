package ru.kpfu.itis.group501.krylov.db1_it_project.entities;

import java.sql.Timestamp;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 9:06 PM
 */
public class Message extends Entity {
    int id;
     User from;
     User to;

     String text;
     Timestamp timestamp;

     boolean read = false;

    public Message(){}

    public Message(User from, User to, String text, Timestamp timestamp) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Message(int id, User from, User to, String text, Timestamp date, boolean read) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.text = text;
        this.timestamp = date;
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
        return timestamp;
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
