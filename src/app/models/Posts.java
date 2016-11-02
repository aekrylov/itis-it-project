package app.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static boolean create(Post post) throws SQLException {
        return create(post, Post.class);
    }
}
