package com.example.interfaces;

import com.example.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserInterface {

    List<User> getSongs();

    User saveUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);


}
