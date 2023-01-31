package com.spring.spring.service;

import com.spring.spring.domain.Role;
import com.spring.spring.domain.User;
import com.spring.spring.repos.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private User user;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private UserRepo userRepo;

//    @MockBean
//    private CustomPasswordEncoder passwordEncoder;

    @Test
    void addUser() {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setEmail("test@mail.com");
        boolean isAddUser = userService.addUser(user);
        Assert.assertTrue(isAddUser);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void addUserFailTest() {
        User user = new User();
        user.setUsername("John");
        user.setPassword("John");
        user.setEmail("John@mail.com");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("John");

        boolean isUserCreated = userService.addUser(user);
        Assert.assertFalse(isUserCreated);
    }

    @Test
    void activateUser() {
        User user = new User();

        user.setUsername("John");
        user.setPassword("John");
        user.setEmail("John@mail.com");
        user.setActivationCode("bingo");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByActivationCode("activate");

        boolean isActivated = userService.activateUser("activate");

        Assert.assertTrue(isActivated);
        Assert.assertNotNull(user.getActivationCode());

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void activateUserFailTest() {
        boolean isUserActivated = userService.activateUser("activate me");

        Assert.assertFalse(isUserActivated);

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}