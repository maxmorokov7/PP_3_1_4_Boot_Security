package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Arrays;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;

    @Autowired()
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String table(Model model,@CurrentSecurityContext(expression = "authentication.principal") User principal) {
        model.addAttribute("table", userService.getAllUsers());
        model.addAttribute("user", principal);
        return "table";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        User user = new User();
        user.setRoles(userService.getRoles());
        model.addAttribute("user", user);
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

//    @GetMapping("/edit")
//    public String updateUser(@RequestParam Integer id, Model model) {
//        User user = userService.getUserById(id);
//        user.setRoles(userService.getRoles());
//        model.addAttribute("user", user);
//        return "edit";
//    }

//    @PostMapping("/edit")
//    public String update(User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "edit";
//        } else {
//            userService.addUser(user);
//            return "redirect:/admin/";
//        }
//    }
@PostMapping("/edit")
public String update(@RequestParam int id, @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam("role") String[] roles) {
    user.setUserId(id);
    user.setRoles(userService.getRoles());
    if (!bindingResult.hasErrors()) {
        userService.updateUser(user);
    }
    return "redirect:/admin/";
}

}

