package app.services;

import app.entities.Conversation;
import app.entities.Message;
import app.entities.User;
import app.models.*;

import java.sql.SQLException;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 6:17 PM
 */
public class ChatService {

    private static ChatService instance = new ChatService();
    private ChatService() {}
    public static ChatService getInstance() {
        return instance;
    }

    private Messages messages  = new Messages();

    public List<Message> getConversation(User thisUser, User thatUser) throws SQLException {
        return messages.getConversation(thisUser, thatUser);

    }

    public boolean sendMessage(Message message) throws SQLException {
        return messages.create(message);
    }

    public List<Conversation> getConversations(User user)throws SQLException {
        return messages.getConversations(user);
    }
}
