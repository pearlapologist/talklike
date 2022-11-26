package com.example.project.controller;

import com.example.project.domain.User;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/getAll")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public String userEditForm(@PathVariable Long id, Model model) {
        User userFromDb = userService.loadUserById(id);

        model.addAttribute("user", userFromDb);
        return "userEdit";
    }

    @GetMapping("/user/save")
    public String userSave(@RequestParam(required=false, name = "username") String username, @RequestParam(required=false, name ="password")  String password,@RequestParam(required=false, name ="email")  String email) {
        User u = new User(username, email, password);
        userService.saveUser(u);

        return "300";
    }

    @PostMapping("/user/save2")
    public String userSave2(@RequestBody User user){
        userService.saveUser(user);
        return "300";
    }
}
