package app.models;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 7:37 PM
 * 11-501
 * Task 
 */
public class Users extends DAO {

    public static User get(String username) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        if(!rs.next())
            throw new NoSuchElementException("User not found");

        return fromResultSet(rs);
    }

    public static User get(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if(!rs.next())
            throw new NoSuchElementException("User not found");

        return fromResultSet(rs);
    }

    public static boolean create(User user) throws SQLException {
        PreparedStatement st = connection.prepareStatement("INSERT INTO users (username, password, name, email)" +
                "VALUES (?, ?, ?, ?)");

        st.setString(1, user.getUsername());
        st.setString(2, user.getPassword());
        st.setString(3, user.getName());
        st.setString(4, user.getEmail());

        return st.executeUpdate() > 0;
    }

    public static String getPassword(String username) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT password FROM users WHERE username = "+username);

        if(!rs.next())
            return null;

        return rs.getString("password");
    }

    public static boolean exists(String username) throws SQLException {
        try {
            get(username);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    static User fromResultSet(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getString("email"),
                rs.getString("photo"), rs.getDouble("rating"));
    }
}
