package org.demo.hibernate.service;

import org.demo.hibernate.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);
    void updateUser(User user, String phoneNumber);
    List<User> getAllUsers();
    User findUserByEmail(String email);
    void delete(int id);
}
