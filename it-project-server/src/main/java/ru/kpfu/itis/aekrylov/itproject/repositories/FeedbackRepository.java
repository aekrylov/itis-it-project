package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.aekrylov.itproject.entities.Feedback;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 7:17 PM
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
