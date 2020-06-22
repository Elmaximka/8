package com.example.crud.controller;

import com.example.crud.model.Role;
import com.example.crud.model.User;
import com.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "registration")
    public String registrationPage(){
        return "registration";
    }

    @PostMapping(value = "registration")
    public String registrationPage(@RequestParam String name, @RequestParam String password,
                                   @RequestParam String lastName, @RequestParam String email,
                                   @RequestParam Long age, @RequestParam String role){
        User user = userService.getUserByName(name);
        if (user == null) {
            user = new User(name, password, lastName, email, age, new Role(role));
            userService.addNewUser(user);
        }
        return "registration";
    }

    @GetMapping(value = "users")
    public String allUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "admin")
    public String adminPage() {
        return "admin";
    }

    @PostMapping(value = "admin")
    public String addUser(@RequestParam String name, @RequestParam String password,
                          @RequestParam String lastName, @RequestParam String email,
                          @RequestParam Long age, @RequestParam String role) {
        User user = userService.getUserByName(name);
        if (user == null) {
            user = new User(name, password, lastName, email, age, new Role(role));
            userService.addNewUser(user);
        }
        return "admin";
    }

    @PostMapping(value = "admin/delete")
    public String deleteUser(@RequestParam String name) {
        userService.deleteUserByName(name);
        return "redirect:/users";
    }

    @PostMapping(value = "admin/change")
    public String changeUser(@RequestParam String name, @RequestParam String lastName,
                             @RequestParam String email, @RequestParam Long age,
                             @RequestParam String password, @RequestParam String role) {
        User user = userService.getUserByName(name);
        if (user != null) {
            if (!lastName.isEmpty()) {
                user.setLastName(lastName);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            if (age != null) {
                user.setAge(age);
            }
            if (!password.isEmpty()) {
                user.setPassword(password);
            }
            if(!role.isEmpty()){
                user.addRole(new Role(role));
            }
            userService.deleteUserByName(name);
            userService.addNewUser(user);
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "user")
    public String userPage(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user.toString());
        return "user";
    }

}