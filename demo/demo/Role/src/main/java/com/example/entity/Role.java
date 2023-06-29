package com.example.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "roles")
@Entity(name = "RoleDTO")
@Data

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rol")
    private int idRol;

    @Column(name="name_of_role")
    private String nameOfRole;

    public Role(String nameOfRole) {
        this.nameOfRole = nameOfRole;
    }
}
