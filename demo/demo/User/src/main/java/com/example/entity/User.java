package com.example.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "UserDTO")
@Table(name = "users")
@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(
            name = "id_user",
            updatable = false
    )
    private String idUser;
    private String lastname;

    private String firstname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "id_user"),inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Role> listOfRoles;

    // TODO add a new interface for admin
    public User(){

    }
    public User(String firstname, String lastname, String email, String username, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.listOfRoles =  new HashSet<Role>();
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername(){
        return username;
    }


}

