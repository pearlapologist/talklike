package com.example.project;

import com.example.project.domain.User;
import com.example.project.repos.UserRepo;
import com.example.project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ProjectApplicationTests {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    //@Test
    void contextLoads() {
        User r = new User("wirigiw", "a@gmail.com", "asdasd");
        r.setActive(true);
        r.setActivationCode(null);
        userRepo.save(r);
    }

   // @Test
    void getAllUsers(){
        Iterable<User> users = userService.findAll();
        System.out.println(users);
    }
}
