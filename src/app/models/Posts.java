package app.models;

import javafx.geometry.Pos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 9:16 PM
 */
public class Posts extends DAO {

    public static int countPosts(User user) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT count(1) FROM posts WHERE \"user\" = ?");
        st.setInt(1, user.getId());

        ResultSet rs = st.executeQuery();

        if(!rs.next()) {
            return -1;
        }

        return rs.getInt(1);
    }

    public static Post get(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM posts WHERE id = ?");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if(!rs.next())
            return null;

        return new Post(rs.getInt("id"), Products.get(rs.getInt("product")),
                Users.get(rs.getInt("user")), rs.getTimestamp("timestamp"));
    }

    public static List<Post> getRecent(int offset, int limit) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT products.*, posts.id pid, posts.timestamp \"timestamp\", " +
                "posts.\"user\" author FROM posts " +
                "JOIN products on posts.product = products.id " +
                "ORDER BY \"timestamp\" DESC OFFSET ? LIMIT ?");

        st.setInt(1, offset);
        st.setInt(2, limit);


        ResultSet rs = st.executeQuery();

        List<Post> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            Product product = fromResultSet(rs, Product.class);
            list.add(new Post(rs.getInt("pid"), product, Users.get(rs.getInt("author")), rs.getTimestamp("timestamp")));
        }

        return list;
    }

    public static List<Post> get(SimpleFilter filter) throws SQLException {
        String sql = "SELECT posts.id pid, \"timestamp\", \"user\", products.* FROM posts " +
                "JOIN products on product = products.id ";

        sql += filter.getWhere();
        PreparedStatement st = connection.prepareStatement(sql);

        List<Object> params = filter.getParams();
        for (int i = 0; i < params.size(); i++) {
            st.setObject(i+1, params.get(i));
        }

        ResultSet rs = st.executeQuery();
        List<Post> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            list.add(new Post(rs.getInt("pid"), fromResultSet(rs, Product.class),
                    Users.get(rs.getInt("user")), rs.getTimestamp("timestamp")));
        }
        return list;
    }


    public static boolean create(Post post) throws SQLException {
        return create(post, Post.class);
    }
}
