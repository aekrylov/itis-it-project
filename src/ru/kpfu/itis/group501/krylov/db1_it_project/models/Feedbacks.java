package ru.kpfu.itis.group501.krylov.db1_it_project.models;

import ru.kpfu.itis.group501.krylov.db1_it_project.entities.BuySell;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Feedback;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.User;

import java.sql.SQLException;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:26 PM
 */
public class Feedbacks extends DAO<Feedback> {
    private DAO<BuySell> buySellDAO = new DAO<>(BuySell.class);

    public List<BuySell> getRecentSells(User seller) throws SQLException {
        return getRecentSells(seller, false);
    }

    public List<BuySell> getRecentSells(User seller, boolean feedbackExists) throws SQLException {
        SimpleFilter filter = new SimpleFilter();
        filter.addSignClause("seller", "=", seller.getId());
        if(feedbackExists)
            filter.addNotNullClause("feedback");

        filter.setOrder("timestamp", false);

        return buySellDAO.get(filter);
    }
}
