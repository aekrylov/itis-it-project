package ru.kpfu.itis.aekrylov.itproject.models;

import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.NotFoundException;
import ru.kpfu.itis.aekrylov.itproject.models.misc.CustomStatement;
import ru.kpfu.itis.aekrylov.itproject.models.misc.SimpleFilter;

import java.sql.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 7:37 PM
 * 11-501
 * Task 
 */
public class Users extends DAO<User> {

    public User get(String username) throws SQLException, NotFoundException {
        CustomStatement statement = statementFactory.selectAll(User.class, true);
        statement.addFilter(
                new SimpleFilter(this).addSignClause("username", "=", username)
        );
        PreparedStatement st = statement.toPS();
        ResultSet rs = st.executeQuery();
        return get(rs, statement);
    }
}
