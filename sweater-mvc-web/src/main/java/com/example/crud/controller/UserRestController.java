package com.example.crud.controller;

import com.example.crud.model.Role;
import com.example.crud.model.User;
import com.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/")
public class UserRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "all")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "user/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PostMapping(value = "edit")
    public void changeUser(@RequestParam String name, @RequestParam String lastName,
                           @RequestParam String email, @RequestParam Long age,
                           @RequestParam String password, @RequestParam String role) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            if (!name.isEmpty()) {
                user.setName(name);
            }
            if (!lastName.isEmpty()) {
                user.setLastName(lastName);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            if (age != null && age > 0) {
                user.setAge(age);
            } else {
                user.setAge(0L);
            }
            if (!password.isEmpty()) {
                user.setPassword(password);
            }
            if (!role.isEmpty()) {
                user.getAuthorities().clear();
                user.addRole(new Role(role));
            }
            userService.addNewUser(user);
        }
    }

    @PostMapping(value = "add")
    public void addUser(@RequestParam String name, @RequestParam String password,
                          @RequestParam String lastName, @RequestParam String email,
                          @RequestParam Long age, @RequestParam String role) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            user = new User(name, password, lastName, email, age, new Role(role));
            userService.addNewUser(user);
        }
    }
}




