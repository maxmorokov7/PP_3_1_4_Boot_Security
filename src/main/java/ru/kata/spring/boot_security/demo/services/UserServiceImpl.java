package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositories userDao;
    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepositories userDAO, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userDao = userDAO;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        return userDao.findById(id).get();
    }

    @Override
    @Transactional
    public void addUser(@Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    @Transactional
    public void removeUser(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public void updateUser(@Valid User user) {
        User user1 = getUserById(user.getUserId());
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        if (!user.getPassword().isEmpty()) {
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user1.setRoles((List<Role>) user.getAuthorities());
        userDao.save(user1);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}