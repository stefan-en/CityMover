package com.example.DTO;

import com.example.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private String idUser;
    private String lastname;
    private String firstname;
    private String email;
    private String username;
    private String password;
    private Set<Role> listOfRoles;
}
