package ru.kpfu.itis.aekrylov.itproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.aekrylov.itproject.entities.BuySell;
import ru.kpfu.itis.aekrylov.itproject.entities.User;

import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 7:13 PM
 */
public interface BuySellRepository extends JpaRepository<BuySell, Integer> {

    List<BuySell> findAllByBuyer(User buyer);
    List<BuySell> findAllBySeller(User seller);
    List<BuySell> findAllByFeedbackNotNullAndSellerOrderByTimestampDesc(User seller);
}
