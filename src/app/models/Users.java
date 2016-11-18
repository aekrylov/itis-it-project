package app.models;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 7:37 PM
 * 11-501
 * Task 
 */
public class Users extends DAO<User> {

    public User get(String username) throws SQLException, NoSuchElementException {
        PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        if(!rs.next())
            throw new NoSuchElementException("User not found");

        return fromResultSet(rs, User.class);
    }
}
