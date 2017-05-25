package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.aekrylov.itproject.entities.BuySell;
import ru.kpfu.itis.aekrylov.itproject.entities.Feedback;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.repositories.BuySellRepository;
import ru.kpfu.itis.aekrylov.itproject.repositories.FeedbackRepository;
import ru.kpfu.itis.aekrylov.itproject.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/18/16 12:33 AM
 */
@Service
public class FeedbackService {

    private FeedbackRepository feedbackRepository;
    private BuySellRepository buySellRepository;
    private UserRepository userRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, BuySellRepository buySellRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.buySellRepository = buySellRepository;
        this.userRepository = userRepository;
    }

    public List<BuySell> getRecentSells(User seller) {
        return buySellRepository.findAllBySeller(seller);
    }

    public List<BuySell> getRecentBuys(User buyer) {
        return buySellRepository.findAllByBuyer(buyer);
    }

    public List<BuySell> getRecentFeedbacks(User seller) {
        return buySellRepository.findAllByFeedbackNotNullAndSellerOrderByTimestampDesc(seller);
    }

    public void leaveFeedback(int bsid, int score, String text) {
        BuySell bs = buySellRepository.findOne(bsid);

        if(score < 1)
            score = 1;
        if(score > 5)
            score = 5;

        User user = bs.getSeller();
        user.addRating(score);
        userRepository.save(user);

        Feedback feedback = new Feedback(text, score);
        feedbackRepository.save(feedback);
        bs.setFeedback(feedback);
        buySellRepository.save(bs);
    }
}
