package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.aekrylov.itproject.entities.User;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 2:16 PM
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
