package com.example.crud.controller;

import com.example.crud.model.Role;
import com.example.crud.model.User;
import com.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "registration")
    public String userPage() {
        User user = userService.getUserByEmail("admin@mail.ru");
        if (user == null) {
            userService.addNewUser(new User("admin", "admin", "admin", "admin@mail.ru", 12L, new Role("ADMIN")));
        }
        return "registration";
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "admin")
    public String adminPage(Model model, ModelMap modelMap, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("userAuth", user);
        modelMap.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping(value = "user")
    public String userPage(Model model, ModelMap modelMap, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("userAuth", user);
        modelMap.addAttribute("users", userService.getAllUsers());
        return "user";
    }

}