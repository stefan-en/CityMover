package com.example.interfaces;

import com.example.entity.Role;

import java.util.List;

public interface RoleInterfaces {

    List<Role> getRoles();
    Role saveRole(Role role);
}
