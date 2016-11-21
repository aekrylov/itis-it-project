package ru.kpfu.itis.group501.krylov.db1_it_project.services;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.CommonHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.User;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.NotFoundException;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ParameterMap;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ValidationException;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.Users;

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

    public boolean create(ParameterMap map) throws ValidationException, SQLException {
        User user = new User(map.get("username"), map.get("password"), map.get("name"), map.get("email"));
        user.validate(true);
        return users.create(user);
    }

    public boolean checkCredentials(String username, String password) throws SQLException {
        String passwordHash = CommonHelpers.encrypt(password);

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

    public boolean updateInfo(User user) throws SQLException {
        return users.update(user);
    }

    public boolean updatePassword(User user, String newPassword) throws SQLException {
        user.setPassword(CommonHelpers.encrypt(newPassword));
        return users.update(user);
    }
}
