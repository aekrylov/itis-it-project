package app.services;

import app.Helpers;
import app.models.Message;
import app.models.Messages;
import app.models.User;
import app.models.Users;

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
}
