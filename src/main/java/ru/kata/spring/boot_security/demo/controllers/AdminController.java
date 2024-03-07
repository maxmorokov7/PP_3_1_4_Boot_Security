//package ru.kata.spring.boot_security.demo.controllers;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.services.RoleService;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//
//    @GetMapping("")
//    public String showAllUsers(Model model) {
//        List<User> users = userService.allUsers();
//        model.addAttribute("users", users);
//        return "allUsers";
//    }
//
//    @GetMapping("/new")
//    public String addNewUser(Model model) {
//        model.addAttribute("user", new User());
//        return "userInfo";
//    }
//
//    @PostMapping("/new")
//    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "userInfo";
//        }
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }
//
//
//    @GetMapping("/update")
//    public String updateUserForm(@RequestParam("id") long id, Model model) {
//        User user = userService.findUserById(id);
//        model.addAttribute("user", user);
//        return "updateUser";
//    }
//
//    @PostMapping("/update")
//    public String saveUpdatedUser(@ModelAttribute("user") @Valid User updatedUser, BindingResult bindingResult) {
//        userService.updateUser(updatedUser);
//        if (bindingResult.hasErrors()) {
//            return "updateUser";
//        }
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/delete")
//    public String deleteForm(Model model, @RequestParam("id") long id) {
//        model.addAttribute("user", userService.findUserById(id));
//        return "deleteUser";
//    }
//
//    @PostMapping("/delete")
//    public String deleteUser(@RequestParam("id") long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
//}
//
//
