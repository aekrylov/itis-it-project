package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kpfu.itis.aekrylov.itproject.entities.Message;
import ru.kpfu.itis.aekrylov.itproject.entities.User;

import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 6:47 PM
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("update Message set read = true where Message.from = :thatUser and Message.to = :thisUser")
    void markRead(User thisUser, User thatUser);

    int countAllByReadFalseAndTo(User to);

    @Query("select m from Message m where m.from in (:user1, :user2) and m.to in (:user1, :user2) order by m.timestamp asc")
    List<Message> getConversation(User user1, User user2);
}
