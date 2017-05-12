package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.beans.factory.annotation.Autowired;
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
import ru.kpfu.itis.aekrylov.itproject.repositories.UserRepository;
import ru.kpfu.itis.aekrylov.itproject.security.UserPrincipal;

import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:36 PM
 */

@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean create(User user) {
        userRepository.save(user);
        return true;
    }

    public User get(String username) throws SQLException, NotFoundException {
        return userRepository.findByUsername(username);
    }

    public User get(int id) {
        return userRepository.findOne(id);
    }

    public boolean exists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public void updateInfo(User user) {
        userRepository.save(user);
    }

    public void updatePassword(User user, String newPassword) throws SQLException {
        user.setPassword(CommonHelpers.encrypt(newPassword));
        userRepository.save(user);
    }

    public void updateAvatar(User user, boolean hasAvatar) throws SQLException {
        user.setHas_avatar(hasAvatar);
        userRepository.save(user);
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
