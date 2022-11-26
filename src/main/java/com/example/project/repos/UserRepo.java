package com.example.project.repos;

import com.example.project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findUserById(Long id);

    User findByActivationCode(String code);

    Page<User> findAll(Pageable pageable);
}
