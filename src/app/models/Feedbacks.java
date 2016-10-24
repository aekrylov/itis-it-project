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
public class Feedbacks extends DAO {

    public static List<Feedback> getRecentFeedbacks(User seller, int limit) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT feedbacks.id as fid, comment, score, feedbacks.date as \"date\", buyer " +
                "from feedbacks " +
                "JOIN buy_sells on feedbacks.buy_sell = buy_sells.id " +
                "WHERE selller = ? " +
                "ORDER BY feedbacks.date DESC " +
                "LIMIT ?");
        st.setInt(1, seller.getId());
        st.setInt(2, limit);

        ResultSet rs = st.executeQuery();
        List<Feedback> res = new ArrayList<>(rs.getFetchSize());

        while (rs.next()) {
            res.add(new Feedback(
                    rs.getInt("fid"),
                    Users.get(rs.getInt("buyer")),
                    rs.getString("comment"),
                    rs.getDate("date"),
                    rs.getInt("score")
            ));
        }

        return res;
    }
}
