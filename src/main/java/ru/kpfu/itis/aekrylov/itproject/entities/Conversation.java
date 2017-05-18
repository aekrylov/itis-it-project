package ru.kpfu.itis.aekrylov.itproject.entities;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 9:21 PM
 */
public class Conversation {

    private User user1;
    private User user2;

    private int unreadCount;

    private Message lastMessage;

    public Conversation(User user1, User user2, long unreadCount, Message lastMessage) {
        this.user1 = user1;
        this.user2 = user2;
        this.unreadCount = (int) unreadCount; //for hibernate query
        this.lastMessage = lastMessage;
    }

    public User getUser1() {
        return user1;
    }
    public User getUser2() {
        return user2;
    }
    public int getUnreadCount() {
        return unreadCount;
    }
    public Message getLastMessage() {
        return lastMessage;
    }
}
