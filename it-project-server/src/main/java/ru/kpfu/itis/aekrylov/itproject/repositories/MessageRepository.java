package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.aekrylov.itproject.entities.Conversation;
import ru.kpfu.itis.aekrylov.itproject.entities.Message;
import ru.kpfu.itis.aekrylov.itproject.entities.User;

import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 6:47 PM
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Modifying
    @Transactional
    @Query("update Message m set read = true where m.from = :thatUser and m.to = :thisUser")
    void markRead(@Param("thisUser") User thisUser, @Param("thatUser") User thatUser);

    int countAllByReadFalseAndTo(User to);

    //not using in operator for correct dialog with self handling
    @Query("select m from Message m where (m.from = :user1 and m.to = :user2) or " +
            "(m.from = :user2 and m.to = :user1) order by m.timestamp asc")
    List<Message> getConversation(@Param("user1") User user1, @Param("user2") User user2);

    @Query("select new ru.kpfu.itis.aekrylov.itproject.entities.Conversation(m.from, m.to, (" +
            "   select count(m1.id) from Message m1 " +
            "   where m1.from in (m.from, m.to) and m1.to = :user and m1.read = false" +
            "), m)" +
            "from Message m " +
            "where m.timestamp = (select max(m2.timestamp) from Message m2" +
            "   where m2.from in (m.from, m.to) and m2.to in (m.from, m.to)" +
            ") and :user in (m.from, m.to)")
    List<Conversation> getConversations(@Param("user") User user);
}
