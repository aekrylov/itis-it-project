package app.services;

import app.entities.User;
import app.models.Users;

import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:36 PM
 */
public class UserService {
    private static UserService ourInstance = new UserService();

    public static UserService getInstance() {
        return ourInstance;
    }

    private Users users = new Users();

    private UserService() {
    }

    public boolean create(User user) throws SQLException {
        return users.create(user);
    }

    public boolean checkCredentials(String username, String password) throws SQLException {
        String passwordHash = app.Helpers.encrypt(password);

        User user = users.get(username);
        return user != null && user.getPassword().equals(passwordHash);
    }

    public User get(String username) throws SQLException {
        return users.get(username);
    }

    public User get(int id) throws SQLException {
        return users.get(id);
    }

    public boolean exists(String username) throws SQLException {
        return users.get(username) != null;
    }
}
