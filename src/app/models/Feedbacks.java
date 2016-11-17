package app.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:26 PM
 */
public class Feedbacks extends DAO<Feedback> {

    public Feedbacks() {
        super("feedbacks", Feedback.class);
    }

    public static List<Feedback> getRecentFeedbacks(User seller, int limit) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT feedbacks.id as id, comment, score, feedbacks.date as \"date\", buyer, buy_sells.id as buy_sell " +
                "from feedbacks " +
                "JOIN buy_sells on feedbacks.buy_sell = buy_sells.id " +
                "WHERE seller = ? " +
                "ORDER BY feedbacks.date DESC " +
                "LIMIT ?");
        st.setInt(1, seller.getId());
        st.setInt(2, limit);

        ResultSet rs = st.executeQuery();
        List<Feedback> res = new ArrayList<>(rs.getFetchSize());

        while (rs.next()) {
            Feedback feedback = fromResultSet(rs, Feedback.class);
            feedback.buy_sell = BuySells.get(rs.getInt("buy_sell"));
            res.add(feedback);
        }

        return res;
    }
}
