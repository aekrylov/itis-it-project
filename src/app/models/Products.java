package app.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/1/16 10:28 PM
 */
public class Products extends DAO {

    public static Product fromResultSet(ResultSet rs) throws SQLException {
        return fromResultSet(rs, Product.class);
    }

    public static Product get(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM products WHERE id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if(!rs.next())
            return null;

        return fromResultSet(rs);
    }
}
