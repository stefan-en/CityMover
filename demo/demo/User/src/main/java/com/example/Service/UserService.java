package com.example.Service;


import com.example.entity.Role;
import com.example.entity.User;
import com.example.interfaces.RoleRepository;
import com.example.interfaces.UserInterface;
import com.example.interfaces.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserInterface {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public List<User> getSongs() {
        return userRepository.findAll();
    }


    @Override
    public User saveUser(User user) {
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setEmail(user.getEmail());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());

        log.info("Save {} to database.\n", user.getFirstname());

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    public void addRoleToUser(String nameOfUser, String roleName) {
        User user = userRepository.findByUsername(nameOfUser);
        Role role = roleRepository.findByNameOfRole(roleName);

        if (role != null){
            log.info("Rolul {} to user {} \n", role, user.getFirstname() );
            user.getListOfRoles().add(role);
        }
        else {
            throw new IllegalArgumentException(String.format("Role %s nu este in baza de date", role));
        }
    }
    public String getUserId(String username){
        User user = userRepository.findByUsername(username);

        if(user != null){
            return user.getIdUser();
        }
        else{
            return "User-ul not found";
        }
    }
}
