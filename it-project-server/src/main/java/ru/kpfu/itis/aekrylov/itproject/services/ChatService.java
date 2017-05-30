package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.aekrylov.itproject.entities.Conversation;
import ru.kpfu.itis.aekrylov.itproject.entities.Message;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.repositories.MessageRepository;
import ru.kpfu.itis.aekrylov.itproject.repositories.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 6:17 PM
 */
@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<Message> getConversation(User thisUser, User thatUser) {
        return messageRepository.getConversation(thisUser, thatUser);
    }

    public void sendMessage(User from, int to, String text) {
        messageRepository.save(
                new Message(from, userRepository.findOne(to), text, Timestamp.from(Instant.now()))
        );
    }

    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Conversation> getConversations(User user) {
        return messageRepository.getConversations(user);
    }

    public int getUnreadCount(User user) {
        return messageRepository.countAllByReadFalseAndTo(user);
    }

    /**
     * Marks read all messages from {@code thatUser} to {@code thisUser}
     * @param thisUser 'to' user
     * @param thatUser 'from' user
     */
    public void markRead(User thisUser, User thatUser) {
        messageRepository.markRead(thisUser, thatUser);
    }

}
