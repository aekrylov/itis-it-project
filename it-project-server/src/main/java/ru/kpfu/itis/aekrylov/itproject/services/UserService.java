package ru.kpfu.itis.aekrylov.itproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.repositories.UserRepository;
import ru.kpfu.itis.aekrylov.itproject.security.UserPrincipal;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:36 PM
 */

@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public boolean create(User user) {
        user.setPassword(encoder.encode(user.getPassword_raw()));
        userRepository.save(user);
        return true;
    }

    public User get(String username) {
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

    public void updatePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    public void updateAvatar(User user, boolean hasAvatar) {
        user.setHas_avatar(hasAvatar);
        userRepository.save(user);
    }

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = get(username);
        if(user == null)
            throw new UsernameNotFoundException("Username not found");
        return new UserPrincipal(user);
    }
}
