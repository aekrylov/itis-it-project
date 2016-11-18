package app.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:16 PM
 */
public class Posts extends DAO<Post> {

    private Users users = new Users();

    public static int countPosts(User user) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT count(1) FROM posts WHERE \"user\" = ?");
        st.setInt(1, user.getId());

        ResultSet rs = st.executeQuery();

        if(!rs.next()) {
            return -1;
        }

        return rs.getInt(1);
    }

    public List<Post> get(SimpleFilter filter) throws SQLException {
        String sql = "SELECT posts.id pid, \"timestamp\", \"user\", products.* FROM posts " +
                "JOIN products on product = products.id ";

        sql += filter.getWhere() + filter.getOrderBy();
        PreparedStatement st = connection.prepareStatement(sql);

        List<Object> params = filter.getParams();
        for (int i = 0; i < params.size(); i++) {
            st.setObject(i+1, params.get(i));
        }

        ResultSet rs = st.executeQuery();
        List<Post> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            list.add(new Post(rs.getInt("pid"), fromResultSet(rs, Product.class),
                    users.get(rs.getInt("user")), rs.getTimestamp("timestamp")));
        }
        return list;
    }
}
