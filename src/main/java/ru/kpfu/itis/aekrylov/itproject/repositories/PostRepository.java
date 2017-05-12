package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;
import ru.kpfu.itis.aekrylov.itproject.entities.User;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 7:05 PM
 */
public interface PostRepository extends JpaRepository<Post, Integer> {

    int countAllByUser(User user);
}
