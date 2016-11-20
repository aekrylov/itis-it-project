package app.services;

import app.entities.User;
import app.misc.NotFoundException;
import app.models.Users;

import java.sql.SQLException;
import java.util.NoSuchElementException;

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

        User user = null;
        try {
            user = users.get(username);
        } catch (NotFoundException ignored) { }
        return user != null && user.getPassword().equals(passwordHash);
    }

    public User get(String username) throws SQLException, NotFoundException {
        return users.get(username);
    }

    public User get(int id) throws SQLException, NotFoundException {
        return users.get(id);
    }

    public boolean exists(String username) throws SQLException {
        try{
            return users.get(username) != null;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
