package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;


    private final RoleService roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleService roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String table(Model model, @CurrentSecurityContext(expression = "authentication.principal") User principal) {
        model.addAttribute("table", userService.getAllUsers());
        model.addAttribute("user", principal);
        return "table";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("user") User user, BindingResult bindingResult,
                      @RequestParam("role") List<String> roleNames) {
        if (!bindingResult.hasErrors()) {
            List<Role> roles = new ArrayList<>();
            for (String roleName : roleNames) {
                Optional<Role> byId = roleRepository.findRoleByName(roleName);
                byId.ifPresent(roles::add);
            }
            user.setRoles(roles);
            userService.addUser(user);
        }
        return "redirect:/admin/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }


    @PostMapping("/edit")
    public String update(@RequestParam int id, User user, BindingResult bindingResult) {
        user.setUserId(id);
        if (!bindingResult.hasErrors()) {
            userService.updateUser(user);
        }
        return "redirect:/admin/";
    }
}