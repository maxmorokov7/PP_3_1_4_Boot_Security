package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;


@Service
public class RegistrationServices {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void register(User user)  {
        userRepository.save(user);
    }
}
