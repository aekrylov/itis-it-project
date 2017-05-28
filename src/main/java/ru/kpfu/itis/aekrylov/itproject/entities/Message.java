package ru.kpfu.itis.aekrylov.itproject.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 9:06 PM
 */
@javax.persistence.Entity
@Table(name = "messages")
public class Message {
    @Id
            @GeneratedValue
    int id;

    @ManyToOne
     User from;

    @ManyToOne
     User to;

     String text;
     Timestamp timestamp = Timestamp.from(Instant.now());

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

    public void setFrom(User from) {
        this.from = from;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
