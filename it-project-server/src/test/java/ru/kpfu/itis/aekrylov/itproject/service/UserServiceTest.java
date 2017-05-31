package ru.kpfu.itis.aekrylov.itproject.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kpfu.itis.aekrylov.itproject.SpringBootTestProfile;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.repositories.UserRepository;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import static org.junit.Assert.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/31/17 9:45 AM
 */

@RunWith(SpringRunner.class) //RunWith can't be moved to meta annotations
@SpringBootTestProfile
public class UserServiceTest {

    private UserService userService;

    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private User user1;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
        this.userService = new UserService(repository, encoder);
    }

    @Before
    public void setUp() {
        repository.deleteAll();
        user1 = new User();
        user1.setName("User 1");
        user1.setEmail("foo@bar.ba");
        user1.setUsername("foobar");
        user1 = repository.save(user1);

        User user2 = new User();
        user2.setName("user 2");
        user2.setEmail("bar@baz.foo");
        user2.setUsername("barbaz");
        repository.save(user2);
    }

    @Test
    public void testLoadsByUsername() {
        assertEquals(userService.get(user1.getUsername()), user1);
    }

    @Test
    public void testLoadsById() {
        assertEquals(userService.get(user1.getId()), user1);
    }

    @Test
    public void testExists() {
        assertFalse(userService.exists("nonexistent"));
        assertTrue(userService.exists("barbaz"));
    }

    @Test
    public void testEncodesPassword() {
        String password = "password";
        User user3 = new User();
        user3.setUsername("user3");
        user3.setEmail("foo@bar.baz");

        user3.setPassword_raw(password);

        userService.save(user3);
        assertTrue(encoder.matches(password, repository.findByUsername(user3.getUsername()).getPassword()));
    }

    @Test
    public void testUpdatesInfo() {
        user1.setName("newname");
        userService.save(user1);
        assertEquals(repository.findByUsername(user1.getUsername()).getName(), "newname");
    }
}
