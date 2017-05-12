package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import ru.kpfu.itis.aekrylov.itproject.entities.Message;
import ru.kpfu.itis.aekrylov.itproject.entities.User;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 6:47 PM
 */
//todo
@NoRepositoryBean
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("update Message set read = true where Message.from = :thatUser and Message.to = :thisUser")
    void markRead(User thisUser, User thatUser);
}
