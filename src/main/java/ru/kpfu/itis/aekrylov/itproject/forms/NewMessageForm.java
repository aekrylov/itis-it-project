package ru.kpfu.itis.aekrylov.itproject.forms;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/24/17 1:40 PM
 */
public class NewMessageForm {

    private int to;
    private String text;

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
