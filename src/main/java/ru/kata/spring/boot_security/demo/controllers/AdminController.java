package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;

    @Autowired()
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String table(Model model) {
        model.addAttribute("table", userService.getAllUsers());
        return "table";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        } else {
            userService.addUser(user);
            return "redirect:/admin/";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("/edit")
    public String updateUser(@RequestParam Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String update(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            userService.updateUser(user);
            return "redirect:/admin/";
        }
    }
}

