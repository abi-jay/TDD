package org.demo.hibernate.service;

import com.sun.source.tree.AssertTree;
import org.demo.hibernate.model.User;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Test
    @Order(1)
    public void createUserTest(){
        User actualUser = new User();
        actualUser.setLastName("Wakanda");
        actualUser.setFirstName("Harry");
        actualUser.setEmail("hwakanda@test.com");
        actualUser.setUserId(UUID.randomUUID().toString());
        UserService userService = new UserServiceImpl();
        userService.createUser(actualUser);

        User expectedUser = userService.findUserByEmail("hwakanda@test.com");
        Assertions.assertEquals(actualUser.getEmail(), expectedUser.getEmail());

    }
    @Test
    @Order(2)
    public void testUserUUID(){
        UserService userService = new UserServiceImpl();
        User expectedUser = userService.findUserByEmail("hwakanda@test.com");
        Assertions.assertNotNull(expectedUser.getUserId());
    }
    @Test
    @Order(3)
    public void deleteUserTest(){
        UserService userService = new UserServiceImpl();
        User actualUser = userService.findUserByEmail("hwakanda@test.com");
        userService.delete(actualUser.getId());
        //User expectedUser = userService.findUserByEmail("hwakanda@test.com");
        //Assertions.assertNull(expectedUser);
        List<User> userList = userService.getAllUsers();
        Assertions.assertFalse(userList.contains(actualUser));
    }
}
