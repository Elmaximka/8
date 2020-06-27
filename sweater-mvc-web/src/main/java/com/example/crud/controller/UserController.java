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
    public String userPage(){
        User user = userService.getUserByEmail("admin@mail.ru");
        if(user == null){
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

    @PostMapping(value = "admin")
    public String addUser(@RequestParam String name, @RequestParam String password,
                          @RequestParam String lastName, @RequestParam String email,
                          @RequestParam Long age, @RequestParam String role) {
        User user = userService.getUserByEmail(name);
        if (user == null) {
            user = new User(name, password, lastName, email, age, new Role(role));
            userService.addNewUser(user);
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "admin/delete")
    public String deleteUser(@RequestParam String email) {
        userService.deleteUserByEmail(email);
        return "redirect:/admin";
    }

    @PostMapping(value = "admin/change")
    public String changeUser(@RequestParam String name, @RequestParam String lastName,
                             @RequestParam String email, @RequestParam Long age,
                             @RequestParam String password, @RequestParam String role) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            user.setId(user.getId());
            if (!name.isEmpty()) {
                user.setName(name);
            }
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
            if (!role.isEmpty()) {
                user.getAuthorities().clear();
                user.addRole(new Role(role));
            }
            userService.deleteUserByEmail(user.getEmail());
            userService.addNewUser(user);
        }
        return "redirect:/admin";
    }


}