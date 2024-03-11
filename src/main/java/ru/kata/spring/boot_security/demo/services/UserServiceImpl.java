package ru.kata.spring.boot_security.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepositories;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepositories userDao;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepositories userDAO, RoleRepository roleRepository) {
        this.userDao = userDAO;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.findById(id).get();
    }

    @Override
    public void addUser(@Valid User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(List.of(roleRepository.findRoleByName("ROLE_USER")));
        userDao.save(user);
    }

    @Override
    public void removeUser(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    public void updateUser(@Valid User user) {
        User user1 = userDao.findById(user.getUserId()).get();
        if (user.getPassword().isEmpty()) {
            user.setPassword(user1.getPassword());
        }else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }
        user.setRole((List<Role>) user1.getAuthorities());
        userDao.save(user);
    }
}