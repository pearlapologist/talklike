package com.example.project.service;

import com.example.project.domain.User;
import com.example.project.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService{
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSenderService mailSenderService;

    public User loadUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User loadUserById(Long id) {
        User userFromDb = userRepo.findUserById(id);
        return userFromDb;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByEmail(user.getEmail());
        if (userFromDb != null) {
            return false;
        }
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        sendMessageToEmail(user);
        return true;
    }

    private void sendMessageToEmail(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to unhinged. Please visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code for unhinged", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActive(true);
        user.setActivationCode(null);
        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Streamable.of(userRepo.findAll())
                .forEach(users::add);
        return users;
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(User user, User newUser) {
        boolean isEmailChanged = (!user.getEmail().equals(newUser.getEmail()) && newUser.getEmail() != null);
        if (isEmailChanged) {
            if (!StringUtils.isEmpty(newUser.getEmail())) {
                newUser.setActivationCode(UUID.randomUUID().toString());
                sendMessageToEmail(newUser);
            }
        }
        userRepo.save(newUser);
    }
}
