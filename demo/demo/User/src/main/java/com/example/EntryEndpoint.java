package com.example;

import com.example.entity.Role;
import com.example.entity.User;

import com.example.Service.RoleService;
import com.example.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@AllArgsConstructor
public class EntryEndpoint {
    public static void main(String[] args) {
        SpringApplication.run(EntryEndpoint.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunnerTicket(UserService userService, RoleService roleService) {
        return args -> {

            userService.saveUser(new User("Puya", "Vasile", "puya@vasile.com","pui","pass"));
            userService.saveUser(new User("BUG", "Mafia", "bug@maf.com","float","ceva"));
            userService.saveUser(new User("Smile", "hagus", "simles@vasile.com","ghita","ceva"));

            roleService.saveRole(new Role("ROLE_ADMIN"));
            roleService.saveRole(new Role("ROLE_MANAGER"));
            roleService.saveRole(new Role("ROLE_USER"));

            //userService.addRoleToUser("pui", "ROLE_ADMIN");
        };
    }

}
