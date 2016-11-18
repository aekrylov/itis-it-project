package app.models;

import app.entities.BuySell;
import app.entities.Feedback;
import app.entities.User;

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

        filter.setOrderBy("timestamp", false);

        return buySellDAO.get(filter);
    }
}
