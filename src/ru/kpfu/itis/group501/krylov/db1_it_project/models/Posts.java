package ru.kpfu.itis.group501.krylov.db1_it_project.models;

import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Post;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Product;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.User;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.NotFoundException;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ReflectiveHelpers;

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

/*
    public List<Post> get(SimpleFilter filter) throws SQLException {
        String sql = "SELECT posts.id pid, \"timestamp\", \"user\", products.* FROM posts " +
                "JOIN products on product = products.id ";

        sql += filter.toSQL();
        PreparedStatement st = connection.prepareStatement(sql);

        filter.fillParams(st);

        ResultSet rs = st.executeQuery();
        List<Post> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            try {
                list.add(new Post(rs.getInt("pid"), ReflectiveHelpers.fromResultSet(rs, Product.class),
                        users.get(rs.getInt("user")), rs.getTimestamp("timestamp")));
            } catch (NotFoundException ignored) { }
        }
        return list;
    }
*/
}
