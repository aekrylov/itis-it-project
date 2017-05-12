package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.aekrylov.itproject.entities.BuySell;
import ru.kpfu.itis.aekrylov.itproject.entities.Feedback;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.NotFoundException;
import ru.kpfu.itis.aekrylov.itproject.models.DAO;
import ru.kpfu.itis.aekrylov.itproject.models.misc.SimpleFilter;
import ru.kpfu.itis.aekrylov.itproject.models.Users;

import java.sql.SQLException;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/18/16 12:33 AM
 */
@Service
public class FeedbackService {

    private static FeedbackService instance = new FeedbackService();
    public static FeedbackService getInstance() {
        return instance;
    }

    private DAO<Feedback> feedbacks = new DAO<>(Feedback.class);
    private DAO<BuySell> buySells = new DAO<>(BuySell.class);
    private Users users = new Users();

    public List<BuySell> getRecentSells(User seller) throws SQLException {
        SimpleFilter filter = new SimpleFilter(buySells);
        filter.addSignClause("seller", "=", seller.getId());
        filter.setOrder("timestamp", false);

        return buySells.get(filter);
    }

    public List<BuySell> getRecentBuys(User buyer) throws SQLException {
        SimpleFilter filter = new SimpleFilter(buySells);
        filter.addSignClause("buyer", "=", buyer.getId());
        return buySells.get(filter);
    }

    public List<BuySell> getRecentFeedbacks(User seller) throws SQLException {
        SimpleFilter filter = new SimpleFilter(buySells);
        filter.addSignClause("seller", "=", seller.getId());
        filter.addNotNullClause("feedback");

        filter.setOrder("timestamp", false);

        return buySells.get(filter);
    }

    public boolean leaveFeedback(int bsid, int score, String text) throws SQLException {
        BuySell bs;
        try {
            bs = buySells.get(bsid);
        } catch (NotFoundException e) {
            return false;
        }
        if(score < 1)
            score = 1;
        if(score > 5)
            score = 5;

        User user = bs.getSeller();
        user.addRating(score);
        users.update(user);

        Feedback feedback = new Feedback(text, score);
        feedbacks.create(feedback);
        bs.setFeedback(feedback);
        return buySells.update(bs);
    }
}
