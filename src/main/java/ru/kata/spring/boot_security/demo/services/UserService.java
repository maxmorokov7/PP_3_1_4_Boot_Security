package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<User> getAllUsers ();
    User getUserById(Integer id);
    void addUser(User user);
    void removeUser(Integer id);
    void updateUser(@Valid User user);
    List<Role> getRoles();
}