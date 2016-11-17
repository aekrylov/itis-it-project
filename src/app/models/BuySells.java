package app.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/15/16 5:48 PM
 */
public class BuySells extends DAO<BuySell> {

    public BuySells() {
        super("buy_sells", BuySell.class);
    }

    public static BuySell get(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM buy_sells WHERE id = ? ");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        rs.next();
        BuySell bs = fromResultSet(rs, BuySell.class);
        bs.seller = Users.get(rs.getInt("seller"));
        bs.buyer = Users.get(rs.getInt("buyer"));
        return bs;

    }
}
