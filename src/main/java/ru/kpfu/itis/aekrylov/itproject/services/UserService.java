package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.NotFoundException;
import ru.kpfu.itis.aekrylov.itproject.misc.ParameterMap;
import ru.kpfu.itis.aekrylov.itproject.misc.ValidationException;
import ru.kpfu.itis.aekrylov.itproject.models.Users;
import ru.kpfu.itis.aekrylov.itproject.security.UserPrincipal;

import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:36 PM
 */

@Service("userService")
public class UserService implements UserDetailsService {
    private static UserService ourInstance = new UserService();

    public static UserService getInstance() {
        return ourInstance;
    }

    private Users users = new Users();

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

    public void updateAvatar(User user, boolean hasAvatar) throws SQLException {
        user.setHas_avatar(hasAvatar);
        users.update(user);
    }

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new UserPrincipal(get(username));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return null;
    }
}
