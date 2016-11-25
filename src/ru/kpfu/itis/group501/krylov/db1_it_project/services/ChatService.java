package ru.kpfu.itis.group501.krylov.db1_it_project.services;

import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Conversation;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Message;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.User;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.NotFoundException;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.Messages;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.Users;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.misc.SimpleFilter;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
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
    private Users users = new Users();

    public List<Message> getConversation(User thisUser, User thatUser) throws SQLException {
        int id1 = thisUser.getId();
        int id2 = thatUser.getId();
        SimpleFilter filter = new SimpleFilter(messages);
        filter.addInClause("from", id1, id2);
        filter.addInClause("to", id1, id2);
        filter.setOrder("timestamp", true);

        return messages.get(filter);
    }

    public boolean sendMessage(User from, int to, String text) throws SQLException {
        try {
            return messages.create(new Message(from, users.get(to), text, Timestamp.from(Instant.now())));
        } catch (NotFoundException e) {
            return false;
        }
    }

    public List<Conversation> getConversations(User user)throws SQLException {
        return messages.getConversations(user);
    }

    public int getUnreadCount(User user) throws SQLException {
        SimpleFilter filter = new SimpleFilter();
        filter.addSignClause("to", "=", user.getId());
        filter.addSignClause("read", "=", false);
        return messages.count(filter);
    }

}
